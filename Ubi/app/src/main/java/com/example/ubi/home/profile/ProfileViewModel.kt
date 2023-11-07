package com.example.ubi.home.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ubi.api.ApiServer
import com.example.ubi.api.response.GuidedResponse
import com.example.ubi.api.response.ProfileResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileViewModel : ViewModel() {
    val TAG = "ProfileViewModel"
    val userInfo = MutableLiveData<ProfileResponse>()
        fun getProfile() = viewModelScope.launch {
        val request = ApiServer.userApi.profileFind()
        request.enqueue(object: Callback<GuidedResponse<ProfileResponse>> {
            override fun onResponse(call: Call<GuidedResponse<ProfileResponse>>, response: Response<GuidedResponse<ProfileResponse>>) {
                if(response.code() == 200) {
                    Log.i(TAG, "${response.body()}")
                }
                Log.d(TAG, "${response.code()}")
            }
            override fun onFailure(call: Call<GuidedResponse<ProfileResponse>>, t: Throwable) {
            }
        })
    }
        val profileImage = File("path/to/image.png").asRequestBody("image/*".toMediaType())
}