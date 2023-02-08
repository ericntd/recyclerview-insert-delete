package com.example.rvlistadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MyListAdapter()
        findViewById<RecyclerView>(R.id.list).adapter = adapter

        val strings = (0..2).map {
            DummyData(it, it.toString())
        }
        adapter.update(strings)
    }
}