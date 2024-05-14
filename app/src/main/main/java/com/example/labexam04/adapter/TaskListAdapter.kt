


// TaskListAdapter.kt
package com.example.labexam04.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.AddTask
import com.example.labexam04.R
import com.example.labexam04.model.TaskListModel
import com.example.labexam04.MainActivity

class TaskListAdapter(private val taskList: List<TaskListModel>, private val context: Context) :
    RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.recycler_task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    /*override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.tid.text = task.tid
        holder.tname.text = task.name

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, AddTask::class.java).apply {
                putExtra("Mode", "E")
                putExtra("Id", task.id)
            }
            if (context is AppCompatActivity) {
                // Check if AddTask activity is already open
                if (context::class.java == AddTask::class.java) {
                    // Finish the activity to prevent restarting
                    context.finish()
                }
            }
            context.startActivity(intent)
        }

    }*/

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.tid.text = task.tid
        holder.tname.text = task.name

        // Set background color based on priority
        val priorityColor = when(task.priority.toLowerCase()) {
            "high" -> R.color.priority_high
            "medium" -> R.color.priority_medium
            "low" -> R.color.priority_low
            else -> android.R.color.transparent // Default color if priority is not recognized
        }
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, priorityColor))

        holder.btnEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddTask::class.java).apply {
                putExtra("Mode", "E")
                putExtra("Id", task.id)
            }
            holder.itemView.context.startActivity(intent)
        }


    }


    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tid: TextView = view.findViewById(R.id.task_id)
        val tname: TextView = view.findViewById(R.id.task_name)
        val btnEdit: Button = view.findViewById(R.id.btn_edit)

    }
}