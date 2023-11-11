package com.example.ubi.api

import android.util.JsonToken
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ubi.api.response.GuidedResponse
import com.example.ubi.api.response.TokenResponse
import com.example.ubi.app.App
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiServer {
    companion object {
        val TAG = "ApiServer"
        private const val url = "http://0.tcp.jp.ngrok.io:18849"
        private val httpClient = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                Log.i(TAG, "${chain.request()}")
                App.tokenPrefs.accessToken?.let {
                    val request = chain.request().newBuilder().addHeader(
                        "Authorization",
                        App.tokenPrefs.accessToken!!
                    ).build()
                    chain.proceed(request)
                } ?: chain.proceed(chain.request())
            }
        }

        private val server = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        val authApi: ApiModel.AuthApi = server.create(ApiModel.AuthApi::class.java)
        val userApi: ApiModel.UserApi = server.create(ApiModel.UserApi::class.java)
        val boardApi: ApiModel.BoardApi = server.create(ApiModel.BoardApi::class.java)
        val friendApi: ApiModel.Friend = server.create(ApiModel.Friend::class.java)
    }
}