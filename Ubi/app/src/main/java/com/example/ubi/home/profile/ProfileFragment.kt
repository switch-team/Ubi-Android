package com.example.ubi.home.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.ubi.R
import com.example.ubi.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel.getProfile()
        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            binding.nameText.text = userInfo.name

        }
        binding.friendButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_findFriendFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}