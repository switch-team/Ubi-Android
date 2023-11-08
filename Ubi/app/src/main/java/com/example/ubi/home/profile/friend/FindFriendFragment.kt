package com.example.ubi.home.profile.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ubi.R
import com.example.ubi.api.response.FriendList
import com.example.ubi.databinding.FragmentFindFriendBinding
import com.example.ubi.databinding.FragmentInviteBinding
import com.example.ubi.home.invite.InviteAdapter
import com.example.ubi.home.invite.InviteDataModel
import com.example.ubi.home.invite.InviteViewModel
import com.example.ubi.home.invite.ReceiveAdapter

class FindFriendFragment : Fragment() {
    private val viewModel by activityViewModels<FindFriendViewModel>()
    private var _binding: FragmentFindFriendBinding? = null
    private lateinit var friendAdapter: FriendAdapter
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFindFriendBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        initInviteRecyclerView()
        return binding.root
    }
    private fun initInviteRecyclerView(){
        friendAdapter = FriendAdapter()
        friendAdapter.notifyItemRemoved(0)
        for(i:Int in 0..viewModel.friendList.size){
            friendAdapter.friendList = viewModel.friendList[i].toMutableList()
            with(binding) {
                recyclerView.adapter = friendAdapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}