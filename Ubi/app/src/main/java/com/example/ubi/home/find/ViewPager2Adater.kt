package com.example.ubi.home.find

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ubi.R
import com.example.ubi.databinding.FragmentFindBinding

class ViewPager2Adater(var list : ArrayList<Int>,var context : Context)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class MyviewHolder(private val binding : FragmentFindBinding) : RecyclerView.ViewHolder(binding.root){
//        var pager  = binding.boardPager
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FragmentFindBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyviewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as MyviewHolder).pager.setBackgroundColor(list.get(position))
    }
}