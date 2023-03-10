package com.example.rvlistadapter

import androidx.recyclerview.widget.RecyclerView
import com.example.rvlistadapter.databinding.ListItemBinding

class MyViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DummyData, listener: ItemDeleteListener) {
        binding.title.text = "${item.content}"
        binding.rank.text = itemView.context.getString(R.string.rank, layoutPosition)

        binding.cta.setOnClickListener {
            listener.delete(layoutPosition)
        }
    }
}