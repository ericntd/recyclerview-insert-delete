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
        ClassicRvAdapter()
    }
    private val numItems = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.list).adapter = adapter

        val data = (0..numItems).map {
            DummyData(it, UUID.randomUUID().toString().replace("-", " "))
        }
        adapter.updateAll(data)
        findViewById<View>(R.id.add).setOnClickListener {
            val randomIndex = Random.nextInt(adapter.itemCount)
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