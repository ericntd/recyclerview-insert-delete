package com.example.rvlistadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rvlistadapter.databinding.ListItemBinding
import kotlin.random.Random

class MyListAdapterDiffUtil : RecyclerView.Adapter<MyViewHolder>() {
    private val tag = "MyListAdapterDiffUtil"
    private val items = mutableListOf<DummyData>()

    class MyDiffCallback(
        private val oldList: List<DummyData>,
        private val newList: List<DummyData>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldCourse: Int, newPosition: Int): Boolean {
            return oldList[oldCourse] == newList[newPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        Log.d(tag, "onBindViewHolder - item at position $position is ${item.content}")

        val listener = object : ItemDeleteListener {
            override fun delete(position: Int) {
                Log.d(tag, "removing item at - $position")
                val newItems = ArrayList(items)
                newItems.removeAt(position)
                updateAll(newItems)
            }
        }
        holder.bind(item, listener)
    }

    fun updateAll(newList: List<DummyData>) {
        val diffCallback = MyDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newList)
        /**
         * Will trigger notifyRangeRemoved and/ or notifyItemRangedInserted internally
         */
        diffResult.dispatchUpdatesTo(AdapterListUpdateCallback(this))
    }

    fun add(dummyData: DummyData, position: Int) {
        val newItems = ArrayList(items)
        newItems.add(position, dummyData)
        updateAll(newItems)
    }
}