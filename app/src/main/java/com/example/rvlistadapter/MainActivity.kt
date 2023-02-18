package com.example.rvlistadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MyListAdapterDiffUtil()
        findViewById<RecyclerView>(R.id.list).adapter = adapter

        val max = 5
        val data = (0..max).map {
            DummyData(it, UUID.randomUUID().toString().replace("-", " "))
        }
        adapter.updateAll(data)
        findViewById<View>(R.id.add).setOnClickListener {
            val num = max + Random.nextInt(max)
            adapter.add(DummyData(num, UUID.randomUUID().toString().replace("-", " ")))
        }
    }
}