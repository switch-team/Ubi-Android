package com.example.ubi.home.finder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubi.databinding.FragmentFinderBinding
import io.socket.client.Socket

class FragmentFinder : Fragment() {
    private var _binding: FragmentFinderBinding? = null
    private val binding  get() = _binding!!
    lateinit var socket : Socket
    val TAG = "FragmentFinder"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinderBinding.inflate(inflater, container, false)
//        try {
//            socket = SocketApplication.get()
//            socket.connect()
//        }
//        catch (e:Exception){
//            Log.d(TAG, "$e")
//        }
        return binding.root
    }
//    var onMessage = Emitter.Listener { args ->
//        val sendtext: TextView = findViewById(R.id.sendtext) as TextView
//        val obj = JSONObject(args[0].toString())
//        val a = sendtext.text.toString()
//        Log.d("main activity", obj.toString())
//        Thread(object : Runnable{
//            override fun run() {
//                runOnUiThread(Runnable {
//                    kotlin.run {
//                        sendtext.text = a + "\n" + obj.get("name") + ": " + obj.get("message")
//                    }
//                })
//            }
//        }).start()
//    }


}