package com.example.rvlistadapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rvlistadapter.databinding.ListItemBinding
import kotlin.random.Random

class MyListAdapter: ListAdapter<DummyData, MyViewHolder>(DiffUtilItemCallback) {
    private val tag = "ListAdapter"

    object DiffUtilItemCallback: DiffUtil.ItemCallback<DummyData>() {
        override fun areItemsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(tag, "onBindViewHolder - $position")
        val item = getItem(position)
        Log.d(tag, "item at position $position is ${item.content}")

        val listener = object : ItemDeleteListener {
            override fun delete(position: Int) {
                Log.d(tag, "removing item at - $position")
                val newItems = mutableListOf<DummyData>()
                newItems.addAll(currentList)
                newItems.removeAt(position)
                submitList(newItems)
            }
        }
        holder.bind(item, listener)
    }

    fun updateAll(newList: List<DummyData>) {
        submitList(newList)
    }

    fun add(dummyData: DummyData, position: Int) {
        val newItems = mutableListOf<DummyData>()
        newItems.addAll(currentList)
        newItems.add(position, dummyData)
        submitList(newItems)
    }
}