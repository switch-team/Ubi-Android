package com.example.ubi.login.contact

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.ubi.databinding.FragmentContactBinding
import kotlin.math.log


class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    lateinit var requestLauncher: ActivityResultLauncher<Intent>
    val TAG = "ContactFragment"
    val cur = requireActivity().contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                // 주소록 상세 정보 가져오기
            }
        }

        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        Log.d(TAG, "${cur?.count}")
        requestLauncher.launch(intent)

        return binding.root
    }
}