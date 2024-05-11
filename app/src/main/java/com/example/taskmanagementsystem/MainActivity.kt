package com.example.taskmanagementsystem

import android.os.Bundle
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanagementsystem.databinding.ActivityMainBinding // Import your ViewBinding class

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Declare View Binding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners using View Binding
        binding.add.setOnClickListener {
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }
        binding.deleteAll.setOnClickListener {
            DataObject.deleteAll()
        }

        // Set RecyclerView adapter and layout manager
        binding.recyclerView.adapter = Adapter(DataObject.getAllData())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
