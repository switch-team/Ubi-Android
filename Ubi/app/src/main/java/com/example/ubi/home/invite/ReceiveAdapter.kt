package com.example.ubi.home.invite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ubi.databinding.InviteItemBinding

class ReceiveAdapter : RecyclerView.Adapter<ReceiveAdapter.MyviewHolder>() {
    var receiveList = mutableListOf<InviteDataModel>()
    inner class MyviewHolder(private val binding : InviteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recyclerModel : InviteDataModel) {
            binding.infoText.text = "초대 메시지 : "
            binding.nameText.text = recyclerModel.name
            binding.contentText.text = recyclerModel.contect
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val binding = InviteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyviewHolder(binding)
    }

    override fun getItemCount() = receiveList.size

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(receiveList[position])
    }
}