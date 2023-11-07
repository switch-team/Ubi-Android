package com.example.ubi.home.notion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ubi.R
import com.example.ubi.databinding.FragmentInviteBinding
import com.example.ubi.databinding.FragmentNotionBinding
import com.example.ubi.home.invite.ReceiveAdapter

class NotionFragment : Fragment() {
    private val viewModel by activityViewModels<NotionViewModel>()
    private var _binding: FragmentNotionBinding? = null
    private lateinit var notionAdapter: NotionAdapter
    private val binding get() = _binding!!
    val notionItemList = mutableListOf<NotionDataModel>()
    val TAG = "InviteFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notionItemList.add(NotionDataModel("이진식 님으로 부터의 알림","초대를 보냈어요!"))
        _binding = FragmentNotionBinding.inflate(inflater, container, false)
        initNotionRecyclerView()
        return binding.root
    }

    private fun initNotionRecyclerView(){
        notionAdapter = NotionAdapter()
        notionAdapter.notifyItemRemoved(0)
        notionAdapter.notionList = notionItemList
        with(binding) {
            recyclerView.adapter = notionAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}