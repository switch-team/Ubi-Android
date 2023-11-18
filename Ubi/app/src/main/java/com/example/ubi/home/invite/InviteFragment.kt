package com.example.ubi.home.invite

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ubi.databinding.FragmentInviteBinding


class InviteFragment : Fragment(){
    private val viewModel by activityViewModels<InviteViewModel>()
    private var _binding: FragmentInviteBinding? = null
    private lateinit var inviteAdapter: InviteAdapter
    private lateinit var receiveAdapter: ReceiveAdapter



    lateinit var requestLauncher: ActivityResultLauncher<Intent>

    private val binding get() = _binding!!
    val inviteItemList = mutableListOf<InviteDataModel>()
    val receiveItemList = mutableListOf<InviteDataModel>()
    val TAG = "InviteFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inviteItemList.add(InviteDataModel("tester", "대기중"))
        receiveItemList.add(InviteDataModel("tester", "빨리와"))
        receiveItemList.add(InviteDataModel("tester", "빨리와"))
        _binding = FragmentInviteBinding.inflate(inflater, container, false)
//        Log.d(TAG, binding.invite.isChecked.toString())
        binding.radioGroup.check(binding.invite.id)
        initInviteRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d(TAG, "test")
            Log.d(TAG, "${it.data!!.data!!.path}")
            val cursor = requireContext().contentResolver.query(
                it.data!!.data!!,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                ),
                null,
                null,
                null
            )
            Log.d(TAG, "${cursor}")

        }

        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        Log.d(TAG, "${intent.data}")
        requestLauncher.launch(intent)


        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkId ->
            when(checkId){
                binding.invite.id -> {
                    Log.d(TAG, "check invite")
                    initInviteRecyclerView()
                }
                binding.receive.id -> {
                    Log.d(TAG, "check receive")
                    initReceiveRecyclerView()
                }

            }
        }
    }
    private fun initInviteRecyclerView(){
        inviteAdapter = InviteAdapter()
        inviteAdapter.notifyItemRemoved(0)
        inviteAdapter.inviteLsit = inviteItemList
        with(binding) {
            recyclerView.adapter = inviteAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun initReceiveRecyclerView(){
        receiveAdapter = ReceiveAdapter()
        receiveAdapter.notifyItemRemoved(0)
        receiveAdapter.receiveList = receiveItemList
        with(binding) {
            recyclerView.adapter = receiveAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}