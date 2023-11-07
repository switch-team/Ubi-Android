package com.example.ubi.home.find

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ubi.api.ApiServer
import com.example.ubi.api.request.PostArticleRequest
import com.example.ubi.api.response.ArticleResponse
import com.example.ubi.api.response.GuidedResponse
import com.example.ubi.api.response.ProfileResponse
import com.google.gson.Gson
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMap.OnMapClickListener
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindViewModel : ViewModel() {
    val TAG = "FindViewModel"

    val articleList = MutableLiveData<List<ArticleResponse>>()
    val location = MutableLiveData<Location>();



    fun getPostList(latitude: Double, longitude: Double) = viewModelScope.launch {
        val request = ApiServer.boardApi.getBoardItemList(latitude, longitude)
        request.enqueue(object : Callback<GuidedResponse<List<ArticleResponse>>> {
            override fun onResponse(
                call: Call<GuidedResponse<List<ArticleResponse>>>,
                response: Response<GuidedResponse<List<ArticleResponse>>>
            ) {
                Log.d(TAG, "${response.code()}")

                if (response.code() == 200) {
                    Log.d(TAG, response.body().toString())
                    response.body()?.data?.let { articleList.value = it }
                }
            }

            override fun onFailure(
                call: Call<GuidedResponse<List<ArticleResponse>>>,
                t: Throwable
            ) {
                Log.d(TAG, "fall")
            }

        })
    }

    fun registPost() {
        val data = Gson().toJson(PostArticleRequest("TITLE", "content", 0.0, 0.0))
            .toRequestBody("application/json".toMediaType())
        ApiServer.boardApi.sendBoard(data).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("TEST", response.body().toString())
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("TEST", "err", t)
            }

        })
    }
}