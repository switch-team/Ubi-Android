package com.example.ubi.home.invite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ubi.databinding.InviteItemBinding

class InviteAdapter(): RecyclerView.Adapter<InviteAdapter.MyviewHolder>() {
    var inviteLsit = mutableListOf<InviteDataModel>()
    inner class MyviewHolder(private val binding : InviteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recyclerModel: InviteDataModel) {
            binding.infoText.text = "상태 : "
            binding.nameText.text = recyclerModel.name
            binding.contentText.text = recyclerModel.contect
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val binding = InviteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyviewHolder(binding)
    }

    override fun getItemCount() = inviteLsit.size
    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(inviteLsit[position])
    }
}