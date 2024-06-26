package com.example.ubi.login.login

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
import com.example.ubi.databinding.FragmentLoginBinding
import com.example.ubi.home.HomeActivity

class LoginFragment : Fragment() {
    val TAG = "LoginFragment"
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            if (binding.idEdit.text.isNotEmpty() && binding.pwdEdit.text.isNotEmpty())
                viewModel.login(binding.idEdit.text.toString(), binding.pwdEdit.text.toString())
        }
        viewModel.token.observe(viewLifecycleOwner) { token ->
            if (token.isNotEmpty()) {
                Log.d(TAG,"test")
                if(true){
                    Log.d(TAG,"test")
                    findNavController().navigate(R.id.action_loginFragment_to_contactFragment)
                }
                else{
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }
}