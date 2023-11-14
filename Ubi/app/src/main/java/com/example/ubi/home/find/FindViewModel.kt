package com.example.ubi.home.find

import android.content.Context
import android.database.Cursor
import android.location.Location
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ubi.api.ApiServer
import com.example.ubi.api.request.PostArticleRequest
import com.example.ubi.api.response.ArticleResponse
import com.example.ubi.api.response.CheckBoardResponse
import com.example.ubi.api.response.GuidedResponse
import com.example.ubi.api.response.ProfileResponse
import com.google.gson.Gson
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMap.OnMapClickListener
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.nio.file.Path

class FindViewModel : ViewModel() {
    val TAG = "FindViewModel"

    val articleList = MutableLiveData<List<ArticleResponse>>()
    val articleInfo = MutableLiveData<CheckBoardResponse?>()
    val location = MutableLiveData<Location>()
    val file = MutableLiveData<Path>()
    val fileName = MutableLiveData<String>()
    val myLableId = MutableLiveData<String>()

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

    fun getArticle(id : String){
        val request = ApiServer.boardApi.checkBoard(id)
        request.enqueue(object : Callback<GuidedResponse<CheckBoardResponse>>{
            override fun onResponse(
                call: Call<GuidedResponse<CheckBoardResponse>>,
                response: Response<GuidedResponse<CheckBoardResponse>>
            ) {
                Log.d(TAG, "${response.code()} ${response.body()}")
                if(response.isSuccessful){
                    response.body()?.data?.let{
                        articleInfo.value  = it
                    }
                }
            }

            override fun onFailure(call: Call<GuidedResponse<CheckBoardResponse>>, t: Throwable) {

            }
        })

    }

}