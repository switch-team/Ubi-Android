package com.example.ubi.home.profile.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ubi.api.response.FriendList
import com.example.ubi.databinding.InviteItemBinding

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.MyviewHolder>() {
    var friendList = mutableListOf<FriendList>()

    inner class MyviewHolder(private val binding : InviteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recyclerModel: FriendList) {
            binding.nameText.text = recyclerModel.name
            binding.infoText.text = recyclerModel.emaile
            binding.contentText.text = recyclerModel.phone

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyviewHolder {
        val binding = InviteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyviewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(friendList[position])
    }

    override fun getItemCount(): Int {
        return friendList.size
    }
}