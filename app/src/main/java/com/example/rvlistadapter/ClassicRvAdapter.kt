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
                Log.d(tag, "removing item at - $position")
                val newItems = mutableListOf<DummyData>()
                newItems.addAll(items)
                newItems.removeAt(position)
                items.clear()
                items.addAll(newItems)
                notifyItemRemoved(position)
                val itemChangedCount = items.size - position
                Log.d(tag, "rerendering $itemChangedCount items")
                notifyItemRangeChanged(position, itemChangedCount)
            }
        }
        holder.bind(item, listener)
    }

    fun updateAll(arrayList: List<DummyData>) {
        items.clear()
        items.addAll(arrayList)
        notifyDataSetChanged()
    }

    fun add(dummyData: DummyData) {
        val newItems = mutableListOf<DummyData>()
        newItems.addAll(items)
        val randomIndex = Random.nextInt(itemCount)
        newItems.add(randomIndex, dummyData)
        val changedCount = itemCount - randomIndex + 1
        Log.d(tag, "item ${dummyData.content} inserted at position $randomIndex")
        items.clear()
        items.addAll(newItems)
        notifyItemInserted(randomIndex)
        notifyItemRangeChanged(randomIndex, changedCount)
    }
}