/*
package com.example.labexam04

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.adapter.TaskListAdapter
import com.example.labexam04.database.DatabaseHelper
import com.example.labexam04.model.TaskListModel

class MainActivity : AppCompatActivity() {

    lateinit var recycler_task : RecyclerView
    lateinit var btn_add : Button
    var tasklistAdapter : TaskListAdapter ?= null
    var dbHandler : DatabaseHelper ?= null
    var tasklist : List<TaskListModel> = ArrayList<TaskListModel>()
    var linearlayoutManager : LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recycler_task = findViewById(R.id.rv_list)
        btn_add = findViewById(R.id.ft_add_items)

        dbHandler = DatabaseHelper(this)
        fetchlist()

        btn_add.setOnClickListener {
            val i = Intent(applicationContext, AddTask::class.java)
            startActivity(i)
        }
    }

    private fun fetchlist() {
        tasklist = dbHandler!!.getAllTask()
        tasklistAdapter = TaskListAdapter(tasklist, applicationContext)
        linearlayoutManager = LinearLayoutManager(applicationContext)
        recycler_task.layoutManager = linearlayoutManager
        recycler_task.adapter = tasklistAdapter
        tasklistAdapter?.notifyDataSetChanged()

    }
}*/

// MainActivity.kt
package com.example.labexam04

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.adapter.TaskListAdapter
import com.example.labexam04.database.DatabaseHelper
import com.example.labexam04.model.TaskListModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerTask: RecyclerView
    private lateinit var btnAdd: Button

    private var taskListAdapter: TaskListAdapter? = null
    private var dbHandler: DatabaseHelper? = null
    private var taskList: List<TaskListModel> = ArrayList()
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerTask = findViewById(R.id.rv_list)
        btnAdd = findViewById(R.id.ft_add_items)


        dbHandler = DatabaseHelper(this)
        fetchList()

        btnAdd.setOnClickListener {
            val intent = Intent(applicationContext, AddTask::class.java)
            startActivity(intent)
        }

    }


    private fun fetchList() {
        taskList = dbHandler?.getAllTasks() ?: emptyList()
        taskListAdapter = TaskListAdapter(taskList, this) // Change applicationContext to this
        linearLayoutManager = LinearLayoutManager(this) // Change applicationContext to this
        recyclerTask.layoutManager = linearLayoutManager
        recyclerTask.adapter = taskListAdapter
        taskListAdapter?.notifyDataSetChanged()
    }

}