package com.example.ubi.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ubi.R
import com.example.ubi.databinding.FragmentWelcomeBinding
import com.example.ubi.home.HomeActivity

class WelcomeFragment : Fragment() {

    private var _binding:FragmentWelcomeBinding? = null
    private val binding  get() = _binding!!

    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginCheck()
        viewModel.checkedLogin.observe(viewLifecycleOwner) {
            if(it or true)
                startActivity(Intent(requireContext(), HomeActivity::class.java))
            else
                Log.d("Login", "NotChecked")
        }

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
    }
}