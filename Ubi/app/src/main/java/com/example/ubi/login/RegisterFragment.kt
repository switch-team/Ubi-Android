package com.example.ubi.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ubi.R
import com.example.ubi.databinding.FragmentRegisterBinding
import com.example.ubi.home.HomeActivity

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<LoginViewModel>()
    val TAG = "RegisterFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener {
            if (binding.phoneEdit.text.isNotEmpty()
                && binding.emailEdit.text.isNotEmpty()
                && binding.passwordEdit.text.isNotEmpty()
                && binding.nameEdit.text.isNotEmpty()
            ) {
                viewModel.register(
                    binding.phoneEdit.text.toString(),
                    binding.emailEdit.text.toString(),
                    binding.passwordEdit.text.toString(),
                    binding.nameEdit.text.toString()
                )
                viewModel.checkedRegister.observe(viewLifecycleOwner) {
                    if (it)
                        findNavController().navigate(R.id.action_registerFragment_to_welcomeFragment)
                    else
                        Log.d(TAG, "NotChecked")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}