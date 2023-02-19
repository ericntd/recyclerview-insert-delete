package com.example.rvlistadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rvlistadapter.databinding.ListItemBinding
import kotlin.random.Random

class ClassicRvAdapter: RecyclerView.Adapter<MyViewHolder>() {
    private val tag = "ClassicRvAdapter"

    private val items = mutableListOf<DummyData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        Log.d(tag, "onBindViewHolder - item at $position is ${item.content}")

        val listener = object : ItemDeleteListener {
            override fun delete(position: Int) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
        holder.bind(item, listener)
    }

    fun updateAll(arrayList: List<DummyData>) {
        items.clear()
        items.addAll(arrayList)
        notifyDataSetChanged()
    }

    fun add(dummyData: DummyData, position: Int) {
        items.add(position, dummyData)
        notifyItemInserted(position)
    }
}