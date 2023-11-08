package com.example.ubi.home.profile.friend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ubi.api.ApiServer
import com.example.ubi.api.response.FriendList
import com.example.ubi.api.response.GuidedResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindFriendViewModel : ViewModel() {
    var friendList = mutableListOf<List<FriendList>>()

    fun getFriendlist() = viewModelScope.launch {
        val request = ApiServer.friendApi.getFriendList()
        request.enqueue(object : Callback<GuidedResponse<List<FriendList>>>{
            override fun onResponse(
                call: Call<GuidedResponse<List<FriendList>>>,
                response: Response<GuidedResponse<List<FriendList>>>
            ) {
                if(response.code() == 200){
                    response.body()!!.data.also { friendList.iterator().apply { it }}
                }
            }

            override fun onFailure(call: Call<GuidedResponse<List<FriendList>>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}