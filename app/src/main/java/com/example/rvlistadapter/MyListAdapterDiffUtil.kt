package com.example.rvlistadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rvlistadapter.databinding.ListItemBinding
import kotlin.random.Random

class MyListAdapterDiffUtil: RecyclerView.Adapter<MyViewHolder>() {
    private val tag = "MyListAdapterDiffUtil"
    private val items = mutableListOf<DummyData>()

    class MyDiffCallback(private val oldList: List<DummyData>, private val newList: List<DummyData>) : DiffUtil.Callback() {
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
                val newItems = mutableListOf<DummyData>()
                newItems.addAll(items)
                newItems.removeAt(position)
                val changedCount = items.size - position
                updateAll(ArrayList(newItems))
                Log.d(tag, "number of item changed position: $changedCount")
                // Trigger onBindViewHolder for the rest of the items that moved in front
                 notifyItemRangeChanged(position, changedCount)
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

    fun add(dummyData: DummyData) {
        val newItems = mutableListOf<DummyData>()
        newItems.addAll(items)
        val randomIndex = Random.nextInt(0, itemCount)
        newItems.add(randomIndex, dummyData)
        val changedCount = itemCount - randomIndex + 1
        updateAll(ArrayList(newItems))
        Log.d(tag, "number of item changed position: $changedCount")
        // Trigger onBindViewHolder for the rest of the items that moved to the end of the list
        notifyItemRangeChanged(randomIndex, changedCount)
    }
}