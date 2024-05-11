package com.example.taskmanagementsystem

import android.content.Intent
import android.view.LayoutInflater
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagementsystem.databinding.ViewBinding

class Adapter(private var data: List<CardInfo>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    // Declare your ViewBinding variable
    private lateinit var binding: ViewBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            // Initialize ViewBinding
            binding = ViewBinding.bind(itemView)
        }

        fun bind(cardInfo: CardInfo) {
            // Access views using ViewBinding
            binding.title.text = cardInfo.title
            binding.priority.text = cardInfo.priority

            // Set background color based on priority
            when (cardInfo.priority.toLowerCase()) {
                "high" -> binding.myLayout.setBackgroundColor(Color.parseColor("#F05454"))
                "medium" -> binding.myLayout.setBackgroundColor(Color.parseColor("#EDC988"))
                else -> binding.myLayout.setBackgroundColor(Color.parseColor("#00917C"))
            }

            // Set click listener
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, UpdateCard::class.java)
                intent.putExtra("id", adapterPosition)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the view using ViewBinding
        val itemView = ViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}