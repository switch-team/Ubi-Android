package com.example.ubi.home.find

import android.content.ContentResolver
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import kotlin.io.path.createTempFile
import kotlin.io.path.readBytes
import kotlin.io.path.writeBytes


class CreateFragment : Fragment() {
    val TAG = "CreateFragment"
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    var targetUri = "".toUri()
    private val viewModel by activityViewModels<FindViewModel>()

    @RequiresApi(Build.VERSION_CODES.Q)
    val request = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {

            // 파일 이름 알아내기
            val cursor = requireContext().contentResolver.query(
                uri, null, null, null, null
            )
            if (cursor != null) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                val fileName = cursor.getString(nameIndex)
                binding.imgView.load(nameIndex)
                Log.i("Kotlin", "선택된 파일 이름: $fileName")
                viewModel.fileName.value = fileName
                // 앱의 캐시 디렉토리에 복사
                val sep = File.separator
                val filePath = "${requireContext().cacheDir.path}$sep$fileName"
                Log.i("Kotlin", "Save Path: $filePath")
                try {
                    binding.imgView.load(uri)
                    val file = File(filePath)
                    val inputStream = requireContext().contentResolver.openInputStream(uri)
                    if (inputStream != null) {
                        file.outputStream().use {
                            it.write(inputStream.readBytes())
                            it.flush()
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        binding.addImg.setOnClickListener {
            request.launch("image/*")
        }
        binding.submitButton.setOnClickListener {
            if (binding.titleEdit.text.isEmpty())
                return@setOnClickListener Toast.makeText(
                    requireContext(),
                    "제목을 입력하여주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            if (binding.contentEdit.text.isEmpty())
                return@setOnClickListener Toast.makeText(
                    requireContext(),
                    "내용을 입력하여주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            val loc = viewModel.location.value
                ?: return@setOnClickListener Log.d(TAG, "Location not found.").let {}
            val body = PostArticleRequest(
                binding.titleEdit.text.toString(),
                binding.contentEdit.text.toString(),
                loc.latitude,
                loc.longitude
            )
            val sep = File.separator
            Log.d(
                TAG,
                "${binding.titleEdit.text}\n${binding.contentEdit.text}\n${loc.latitude}\n${loc.longitude}"
            )
            val filePath = "${requireContext().cacheDir.path}$sep${viewModel.fileName.value}"
            Log.i("Kotlin", "Save Path: $filePath")
            try {
                val file = File(filePath)
                ApiServer.boardApi.sendBoard(
                    Gson().toJson(body).toRequestBody("application/json".toMediaType()),
                    getImageBody("thumbnailImage", file)
                ).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {

                        if (!response.isSuccessful) {
                            val data = response.errorBody()
                                ?.let { Gson().fromJson(it.toString(), GuidedResponse::class.java) }
                            Toast.makeText(
                                requireContext(),
                                data?.message ?: "게시글 게시에 실패하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }
                        Log.d(TAG, "${viewModel.fileName.value}")
                        Log.d(TAG, "게시 성공!")
                        findNavController().navigate(R.id.action_createFragment_to_findFragment)
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.d(TAG, "Failed to send the article info", t)
                        Toast.makeText(requireContext(), "게시글 게시에 실패하였습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return binding.root
    }

    fun getImageBody(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("image/*".toMediaType())
        )
    }
}