package com.example.ubi.home.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.ubi.R

class PermissionFragment : Fragment() {
    val TAG = "PermissionFragment"

    private val request = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ map ->
        map.forEach {
            if(!it.value)
                requireActivity().finish()
        }
        Log.d(TAG, "forEach")
        findNavController().navigate(R.id.action_permissionFragment_to_welcomeFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permissions = mutableListOf<String>()
        if(PackageManager.PERMISSION_GRANTED != requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        if(PackageManager.PERMISSION_GRANTED != requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION))
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if(PackageManager.PERMISSION_GRANTED != requireActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS))
            permissions.add(Manifest.permission.READ_CONTACTS)

        if(permissions.size == 0) {
            Log.d(TAG, "PermissionFragment")
            findNavController().navigate(R.id.action_permissionFragment_to_welcomeFragment)
        }
        else {
            Log.d(TAG, "${permissions.size}")
            request.launch(permissions.toTypedArray())
        }

    }
}