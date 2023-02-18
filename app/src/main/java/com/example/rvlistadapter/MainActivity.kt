package com.example.rvlistadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.min
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

    /**
     * Must be smaller than size of [DB.countries]
     */
    private val numItems = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.list).adapter = adapter

        /*
        Initialize list view with numItems random subset of countries
         */
        val randomStart = Random.nextInt(DB.countries.size)
        val end = min(randomStart + numItems, DB.countries.size - 1)
        val data = DB.countries.mapIndexed { index: Int, s: String ->
            DummyData(id = index, content = s)
        }.shuffled().subList(randomStart, end)
        adapter.updateAll(data)

        /*
        Add random UUID string, there should be no chance of duplication
         */
        findViewById<View>(R.id.add).setOnClickListener {
            val randomIndex = if (adapter.itemCount > 0) {
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