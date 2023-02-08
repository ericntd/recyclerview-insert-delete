package com.example.rvlistadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

class MyListAdapter: RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private val tag = "ListAdapter"
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

    interface ItemDeleteListener {
        fun delete(position: Int)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: DummyData) {
            itemView.findViewById<TextView>(R.id.title).text = item.content
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(tag, "onBindViewHolder - $position")
        val item = items[position]

        val listener = object : ItemDeleteListener {
            override fun delete(position: Int) {
                Log.d(tag, "removing item at - $position")
                val newItems = mutableListOf<DummyData>()
                newItems.addAll(items)
                newItems.removeAt(position)
                update(ArrayList(newItems))
            }
        }
        holder.itemView.findViewById<View>(R.id.cta).setOnClickListener {
            listener.delete(position)
        }
        holder.bind(item)
    }

    fun update(newList: List<DummyData>) {
        val diffCallback = MyDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(AdapterListUpdateCallback(this))
    }
}