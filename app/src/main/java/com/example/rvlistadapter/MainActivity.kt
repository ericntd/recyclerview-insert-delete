package com.example.rvlistadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    /**
     * 3 options
     * - [ClassicRvAdapter]
     * - [MyListAdapterDiffUtil]: classic RecyclerView Adapter with DiffUtil
     * - [MyListAdapter]: Jetpack's RecyclerView Adapter with built-in DiffUtil and data encapsulation
     */
    private val adapter by lazy {
        MyListAdapter()
    }
    private val numItems = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.list).adapter = adapter

        val data = (0 until numItems).map {
            DummyData(it, UUID.randomUUID().toString().replace("-", " "))
        }
        adapter.updateAll(data)

        findViewById<View>(R.id.add).setOnClickListener {
            val randomIndex = if (adapter.itemCount>0) {
                Random.nextInt(adapter.itemCount)
            } else {
                0
            }
            adapter.add(
                DummyData(
                    id = numItems + Random.nextInt(numItems),
                    UUID.randomUUID().toString().replace("-", " ")
                ),
                position = randomIndex
            )
        }
    }
}