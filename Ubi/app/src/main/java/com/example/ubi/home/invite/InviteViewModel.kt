package com.example.ubi.home.invite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ubi.api.ApiServer
import com.example.ubi.api.response.FriendList
import com.example.ubi.api.response.GuidedResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InviteViewModel : ViewModel() {
    val TAG = "InvitedViewModel"
    fun getInviteList()=viewModelScope.launch {
        val request = ApiServer.friendApi.getFriendList()
        request.enqueue(object : Callback<GuidedResponse<List<FriendList>>>{
            override fun onResponse(
                call: Call<GuidedResponse<List<FriendList>>>,
                response: Response<GuidedResponse<List<FriendList>>>
            ) {
                Log.i(TAG, response.code().toString())
                if(response.code() == 200) {
                    Log.i(TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<GuidedResponse<List<FriendList>>>, t: Throwable) {
                Log.i(TAG, "fall")
            }

        })
    }
    fun getReceiveList()=viewModelScope.launch {
        val request = ApiServer.friendApi.getFriendList()
        request.enqueue(object : Callback<GuidedResponse<List<FriendList>>>{
            override fun onResponse(
                call: Call<GuidedResponse<List<FriendList>>>,
                response: Response<GuidedResponse<List<FriendList>>>
            ) {
                Log.i(TAG, response.code().toString())
                if(response.code() == 200) {
                    Log.i(TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<GuidedResponse<List<FriendList>>>, t: Throwable) {
                Log.i(TAG, "fall")
            }

        })
    }
}