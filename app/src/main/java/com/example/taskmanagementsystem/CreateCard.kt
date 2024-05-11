package com.example.taskmanagementsystem

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanagementsystem.databinding.ActivityCreateCardBinding

class CreateCard : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCardBinding // Declare View Binding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for the save button
        binding.saveButton.setOnClickListener {
            // Check if title and priority are not empty
            if (binding.createTitle.text.toString().trim().isNotEmpty() &&
                binding.createPriority.text.toString().trim().isNotEmpty()) {
                val title = binding.createTitle.text.toString()
                val priority = binding.createPriority.text.toString()

                // Save data using DataObject (assuming it's a singleton or similar)
                DataObject.SetData(title, priority)

                // Start MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
