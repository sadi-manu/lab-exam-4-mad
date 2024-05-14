/*
package com.example.labexam04

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.labexam04.database.DatabaseHelper
import com.example.labexam04.model.TaskListModel

class AddTask : AppCompatActivity() {

    lateinit var btn_save : Button
    lateinit var btn_del :Button
    lateinit var et_name : EditText
    lateinit var et_details : EditText
    var dbHandler : DatabaseHelper? = null
    var isEditMode : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn_save = findViewById(R.id.btn_save)
        btn_del = findViewById(R.id.btn_del)
        et_name = findViewById(R.id.et_name)
        et_details = findViewById(R.id.et_details)

        dbHandler = DatabaseHelper(this)

        if (intent != null && intent.getStringExtra("Mode") == "E") {
            //update data
            isEditMode = true
            btn_save.text = "Update data"
            btn_del.visibility = View.VISIBLE
            val tasks : TaskListModel = dbHandler!!.getTask(intent.getIntExtra("Id", 0))
            et_name.setText(tasks.name)
            et_details.setText(tasks.details)

        } else {
            //insert new data
            isEditMode = false
            btn_save.text = "Save data"
            btn_del.visibility = View.GONE
        }

        btn_save.setOnClickListener {
            var success:Boolean = false
            val tasks : TaskListModel = TaskListModel()
            if(isEditMode) {
                //update
                tasks.id = intent.getIntExtra("Id", 0)
                tasks.name = et_name.text.toString()
                tasks.details = et_details.text.toString()

                success = dbHandler?.updateTask(tasks) as Boolean

            }else {
                //insert
                tasks.name = et_name.text.toString()
                tasks.details = et_details.text.toString()

                success = dbHandler?.addTask(tasks) as Boolean
            }

            if (success) {
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Something went wrong!!", Toast.LENGTH_LONG).show()
            }
        }
        btn_del.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click Yes if you want to delete the task")
                .setPositiveButton("YES", {dialog, i ->
                    val success = dbHandler?.deleteTask(intent.getIntExtra("Id", 0)) as Boolean
                    if (success)
                        finish()
                    dialog.dismiss()
                })
                .setNegativeButton("No", {dialog, i ->
                    dialog.dismiss()
                })
            dialog.show()
        }
    }
}*/


// AddTask.kt
package com.example.labexam04

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.labexam04.database.DatabaseHelper
import com.example.labexam04.model.TaskListModel

class AddTask : AppCompatActivity() {

    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var ettid: EditText
    private lateinit var etName: EditText
    private lateinit var etDetails: EditText
    private lateinit var etsupervisor: EditText
    private lateinit var etduration: EditText
    private lateinit var sppriority: Spinner
    private var dbHandler: DatabaseHelper? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSave = findViewById(R.id.btn_save)
        btnDelete = findViewById(R.id.btn_del)
        ettid = findViewById(R.id.et_tid)
        etName = findViewById(R.id.et_name)
        etDetails = findViewById(R.id.et_details)
        etsupervisor = findViewById(R.id.et_supervisor)
        etduration = findViewById((R.id.et_duration))
        sppriority = findViewById(R.id.spinner_priority)
        dbHandler = DatabaseHelper(this)

        if (intent != null && intent.getStringExtra("Mode") == "E") {
            // Update data
            isEditMode = true
            btnSave.text = "Update data"
            btnDelete.visibility = View.VISIBLE
            val task: TaskListModel = dbHandler!!.getTask(intent.getIntExtra("Id", 0))
            ettid.setText(task.tid)
            etName.setText(task.name)
            etDetails.setText(task.details)
            etsupervisor.setText(task.supervisor)
            etduration.setText(task.duration)


        } else {
            // Insert new data
            isEditMode = false
            btnSave.text = "Save data"
            btnDelete.visibility = View.GONE
        }

        btnSave.setOnClickListener {
            var success = false
            val task = TaskListModel().apply {
                if (isEditMode) {
                    id = intent.getIntExtra("Id", 0)
                }
                tid = ettid.text.toString()
                name = etName.text.toString()
                details = etDetails.text.toString()
                supervisor = etsupervisor.text.toString()
                duration = etduration.text.toString()
                priority = sppriority.selectedItem.toString()
            }

            success = if (isEditMode) {
                dbHandler?.updateTask(task) ?: false
            } else {
                dbHandler?.addTask(task) ?: false
            }

            if (success) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext, "Something went wrong!!", Toast.LENGTH_LONG).show()
            }
        }

        btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click Yes if you want to delete the task")
                .setPositiveButton("YES") { dialog, _ ->
                    val success = dbHandler?.deleteTask(intent.getIntExtra("Id", 0)) ?: false
                    if (success)
                        finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            dialog.show()
        }
    }


}