package com.example.ubi

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.manager.TargetTracker
import com.example.ubi.databinding.FragmentCreateBinding
import com.example.ubi.databinding.FragmentFindBinding


class CreateFragment : Fragment() {
    val TAG = "CreateFragment"
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    var targetUri = "".toUri()
    private lateinit var launcher: ActivityResultLauncher<Intent>
    val request = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        Log.d(TAG, "in ${uri}")
        binding.imgView.setImageURI(uri)
        if (uri != null) {
            targetUri = uri
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        binding.addImg.setOnClickListener{
            request.launch("image/*")
        }
        binding.submitButton.setOnClickListener {
            findNavController().navigate(R.id.action_createFragment_to_findFragment)
        }

        return binding.root
    }

}