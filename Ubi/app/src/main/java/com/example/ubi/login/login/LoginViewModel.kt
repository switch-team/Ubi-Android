package com.example.ubi.login.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ubi.api.request.LoginRequest
import com.example.ubi.api.ApiServer
import com.example.ubi.api.response.GuidedResponse
import com.example.ubi.api.response.RegisterResponse
import com.example.ubi.api.response.TokenResponse
import com.example.ubi.app.App
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val TAG = "LoginViewModel"
    val error = MutableLiveData<String>()
    val token = MutableLiveData<String>().apply { value = "" }
    val checkedLogin = MutableLiveData<Boolean>().apply { value = false }
    val checkedRegister = MutableLiveData<Boolean>().apply { value = false }

    val tokenPrefs = App.tokenPrefs

    fun loginCheck() = viewModelScope.launch {
        if (tokenPrefs.accessToken != null) {
            Log.d(TAG, "Token Exists: ${tokenPrefs.accessToken}")
            // 서버에 로그인 체크
            val response = ApiServer.authApi.loginCheck(tokenPrefs.accessToken!!)
            response.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.code() == 200) {
                        checkedLogin.value = true
                        Log.d(TAG, "Logged in!")
                        return
                    }
                    tokenPrefs.removeToken()
                    token.value = ""
                    Log.d(TAG, "${token.value}")
                    Log.d(TAG, "${response.code()} login")
                    Log.d(TAG, "${response.errorBody()?.string()}")
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                }
            })
        } else {
            Log.d(TAG, "Token Null")
        }
    }

    fun login(id: String, password: String) = viewModelScope.launch {
        val response = ApiServer.authApi.login(LoginRequest(id, password))
        response.enqueue(object : Callback<GuidedResponse<TokenResponse>> {
            override fun onResponse(
                call: Call<GuidedResponse<TokenResponse>>,
                response: Response<GuidedResponse<TokenResponse>>
            ) {
                Log.d(TAG, "${response.errorBody()?.string()}")
                val n = response.body() ?: return
                if (response.code() == 200) {
                    token.value = n.data!!.token
                    tokenPrefs.accessToken = token.value
                    Log.d(TAG, "success: ${token.value}\n${tokenPrefs.accessToken} ")
                } else {
                    error.value = n.message
                    Log.d(TAG, "${response.errorBody()?.string()}")
                    Log.d(TAG, "${n.message} test")
                }
            }

            override fun onFailure(call: Call<GuidedResponse<TokenResponse>>, t: Throwable) {
                Log.d(TAG, "failure", t)
            }
        })
    }

    fun register(phone: String, email: String, password: String, name: String) {
        Log.d(TAG, "${RegisterResponse(phone, email, password, name)}")
        var request = ApiServer.authApi.register(RegisterResponse(phone, email, password, name))
        request.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.code() == 200) {
                    Log.d(TAG, "${response.body()}")
                    checkedRegister.value = true

                } else {
                    Log.d(TAG, "${response.errorBody()?.string()}, errer")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, "${request.hashCode()}")
            }
        })
    }
}