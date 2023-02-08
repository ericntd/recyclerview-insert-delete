package com.example.rvlistadapter

import android.util.Log
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

class ExampleListUpdateCallback<T : RecyclerView.ViewHolder?>(val adapter: RecyclerView.Adapter<T>) :
    ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {
        this.adapter.notifyItemRangeChanged(position, count, payload)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        this.adapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onInserted(position: Int, count: Int) {
        this.adapter.notifyItemRangeInserted(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        Log.d("ExampleListUpdateCallback", "onRemoved $position - $count")
        this.adapter.notifyItemRangeRemoved(position, count)
    }
}