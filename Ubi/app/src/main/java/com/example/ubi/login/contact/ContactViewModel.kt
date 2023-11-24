package com.example.ubi.login.contact

import android.app.Activity.RESULT_OK

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.ubi.api.ApiServer
import com.example.ubi.api.request.UserCheckRequest
import com.example.ubi.api.response.GuidedResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactViewModel:ViewModel() {
    val TAG = "ContactViewModel"
    val responseCheckUser = MutableLiveData<List<UserCheckRequest>?>()
    fun userCheck(getUserList:List<String>)=viewModelScope.launch{
        val request = ApiServer.userApi.userCheck(getUserList)
        request.enqueue(object : Callback<GuidedResponse<List<UserCheckRequest>>>{
            override fun onResponse(
                call: Call<GuidedResponse<List<UserCheckRequest>>>,
                response: Response<GuidedResponse<List<UserCheckRequest>>>,
            ) {
                if(response.body() != null){
                    responseCheckUser.value = response.body()!!.data
                }
                else{
                    Log.d(TAG, "${response.code()} ${response.body()}")
                }
            }

            override fun onFailure(
                call: Call<GuidedResponse<List<UserCheckRequest>>>,
                t: Throwable,
            ) {
                Log.d(TAG, "failure", t)
            }

        })
    }
}