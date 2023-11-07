package com.example.ubi

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class SingletonQueue(context: Context) {
    companion object{
        private var INSTANCE:SingletonQueue? = null
        fun getInstance(context:Context) =
            INSTANCE ?: synchronized(this){
                val instance = SingletonQueue(context)
                INSTANCE=instance
                instance
            }
    }
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext) }

}