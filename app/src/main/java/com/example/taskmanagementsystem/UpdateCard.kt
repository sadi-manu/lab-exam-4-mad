package com.example.taskmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskmanagementsystem.databinding.ActivityUpdateCardBinding // Import your ViewBinding class
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCard : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateCardBinding // Declare View Binding variable
//sadith
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = DataObject.getData(pos).title
            val priority = DataObject.getData(pos).priority
            binding.createTitle.setText(title)
            binding.createPriority.setText(priority)

            binding.deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                myIntent()
            }

            binding.updateButton.setOnClickListener{
                DataObject.updateData(pos, binding.createTitle.text.toString(), binding.createPriority.text.toString())
                Toast.makeText(this, "$title $priority", Toast.LENGTH_LONG).show()
                myIntent()
            }
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
