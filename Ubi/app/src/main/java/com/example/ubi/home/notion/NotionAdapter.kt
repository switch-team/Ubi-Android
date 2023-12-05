package com.example.ubi.home.notion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ubi.databinding.NotionItemBinding

class NotionAdapter(
) : RecyclerView.Adapter<NotionAdapter.MyViewHolder>() {
    var contactList = mutableListOf<String>()

    inner class MyViewHolder(private val binding: NotionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recyclerModel: String) {
            binding.contentText.text = recyclerModel
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NotionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun getItemCount() = contactList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactList[position])
    }
}