package com.example.ubi.home.find

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.ubi.R
import com.example.ubi.api.ApiServer
import com.example.ubi.api.request.PostArticleRequest
import com.example.ubi.api.response.GuidedResponse
import com.example.ubi.databinding.FragmentCreateBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.io.path.createTempFile
import kotlin.io.path.readBytes
import kotlin.io.path.writeBytes


class CreateFragment : Fragment() {
    val TAG = "CreateFragment"
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    var targetUri = "".toUri()
    private val viewModel by activityViewModels<FindViewModel>()

    val request = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        Log.d(TAG, "in $uri")
        if (uri != null) {
//            binding.icImg.load(uri)
//            targetUri = uri
//            uriPrefs.uri = uri.toString()
//            viewModel.uri.value = uri
            val inputStream = requireContext().contentResolver.openInputStream(uri) ?:
            throw RuntimeException("cannot open inputstream.")
            val file = createTempFile(prefix = "${System.currentTimeMillis()}", suffix = ".png")
            file.writeBytes(inputStream.readBytes())
            Log.d(TAG, file.toString())
            binding.imgView.load(file.toString()) {
                size(600, 400)
            }
            viewModel.file.value = file
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        binding.addImg.setOnClickListener {
            request.launch("image/*")
        }
        binding.submitButton.setOnClickListener {
            if (binding.titleEdit.text.isEmpty())
                return@setOnClickListener Toast.makeText(requireContext(), "제목을 입력하여주세요.", Toast.LENGTH_SHORT).show()
            if (binding.contentEdit.text.isEmpty())
                return@setOnClickListener Toast.makeText(requireContext(), "내용을 입력하여주세요.", Toast.LENGTH_SHORT).show()
            val loc = viewModel.location.value ?: return@setOnClickListener Log.d(TAG, "Location not found.").let {}
            val body = PostArticleRequest(
                binding.titleEdit.text.toString(),
                binding.contentEdit.text.toString(),
                loc.latitude,
                loc.longitude
            )
            ApiServer.boardApi.sendBoard(
                Gson().toJson(body).toRequestBody("application/json".toMediaType()),
                viewModel.file.value?.readBytes()?.toRequestBody("image/*".toMediaType())
            ).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (!response.isSuccessful) {
                        val data = response.errorBody()?.let { Gson().fromJson(it.toString(), GuidedResponse::class.java) }
                        Toast.makeText(requireContext(), data?.message ?: "게시글 게시에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                    Log.d(TAG, "${viewModel.file.value}")
                    Log.d(TAG, "게시 성공!")
                    findNavController().navigate(R.id.action_createFragment_to_findFragment)
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.d(TAG, "Failed to send the article info", t)
                    Toast.makeText(requireContext(), "게시글 게시에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return binding.root
    }

}