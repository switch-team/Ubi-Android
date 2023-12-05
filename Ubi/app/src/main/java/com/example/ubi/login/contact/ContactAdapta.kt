package com.example.ubi.login.contact

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.ubi.R
import com.example.ubi.api.request.UserCheckRequest
import com.example.ubi.databinding.InviteItemBinding
import com.example.ubi.databinding.NotionItemBinding
import com.example.ubi.home.notion.NotionAdapter
import com.example.ubi.login.login.LoginViewModel

class ContactAdapta : RecyclerView.Adapter<ContactAdapta.MyViewHolder>() {
    val selectedContact = arrayListOf<UserCheckRequest>()



    var contactList = arrayListOf<UserCheckRequest>()
    val TAG = "ContactAdapta"


    inner class MyViewHolder(private val binding: NotionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recyclerModel: UserCheckRequest, position: Int) {
            binding.contentText.text = recyclerModel.users
            binding.root.setOnClickListener{
                changeBackground(binding, position)
                Log.d(TAG, "$position")
            }
        }

    }

    private fun changeBackground(binding: NotionItemBinding, position: Int) {
//        binding.board.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.drawable.item_on_click))
        if (selectedContact.contains(contactList[position])){
            binding.board.setBackgroundResource(R.drawable.itembackground)
            selectedContact.remove(contactList[position])
        }
        else {
            binding.board.setBackgroundResource(R.drawable.item_on_click)
            selectedContact.add(contactList[position])
        }
        Log.d(TAG, "$selectedContact")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NotionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactList[position], position)
    }
}