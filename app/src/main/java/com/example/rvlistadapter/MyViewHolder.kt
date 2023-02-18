package com.example.rvlistadapter

import androidx.recyclerview.widget.RecyclerView
import com.example.rvlistadapter.databinding.ListItemBinding

class MyViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DummyData, listener: ItemDeleteListener) {
        binding.title.text = "data: ${item.content} at position $absoluteAdapterPosition"

        binding.cta.setOnClickListener {
            listener.delete(absoluteAdapterPosition)
        }
    }

}