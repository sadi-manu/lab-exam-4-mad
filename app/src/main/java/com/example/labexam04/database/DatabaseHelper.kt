/*
package com.example.labexam04.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.labexam04.model.TaskListModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "task"
        private val DB_VERSION = 1
        private val TABLE_NAME = "tasklist"
        private val ID = "id"
        private val TASK_NAME = "taskname"
        private val TASK_DETAILS = "taskdetails"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $TASK_NAME TEXT, $TASK_DETAILS TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllTask(): List<TaskListModel> {
        val tasklist = ArrayList<TaskListModel>()
        val bd = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = bd.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val tasks = TaskListModel()
                    tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    tasks.name = cursor.getString(cursor.getColumnIndex(TABLE_NAME))
                    tasks.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
                    tasklist.add(tasks)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return tasklist
    }
    //insert
    fun addTask(tasks : TaskListModel) : Boolean {
        val bd = this.writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME, tasks.details)
        val _success = bd.insert(TABLE_NAME, null, values)
        bd.close()
        return (Integer.parseInt("$_success") != -1)
    }

    //select the data of particular id
    @SuppressLint("Range")
    fun getTask(_id:Int) : TaskListModel {
        val tasks = TaskListModel()
        val bd = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = bd.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        tasks.name = cursor.getString(cursor.getColumnIndex(TABLE_NAME))
        tasks.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
        cursor.close()
        return tasks
    }

    fun deleteTask(_id: Int) : Boolean {
        val bd = this.writableDatabase
        val _success = bd.delete(TABLE_NAME, ID+"=?", arrayOf(_id.toString())).toLong()
        bd.close()
        return Integer.parseInt("$_success") != -1
    }

    fun updateTask(tasks: TaskListModel) : Boolean {
        val bd = this.writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME, tasks.name)
        values.put(TASK_DETAILS, tasks.details)
        val _success = bd.update(TABLE_NAME, values, ID+"=?", arrayOf(tasks.id.toString())).toLong()
        bd.close()
        return Integer.parseInt("$_success") != -1
    }
}*/

// DatabaseHelper.kt
package com.example.labexam04.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.labexam04.model.TaskListModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "task"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "tasklist"
        private const val ID = "id"
        private const val TASK_NAME = "taskname"
        private const val TASK_DETAILS = "taskdetails"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $TASK_NAME TEXT, $TASK_DETAILS TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun getAllTasks(): List<TaskListModel> {
        val taskList = ArrayList<TaskListModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.use {
            while (it.moveToNext()) {
                val idIndex = it.getColumnIndex(ID)
                val nameIndex = it.getColumnIndex(TASK_NAME)
                val detailsIndex = it.getColumnIndex(TASK_DETAILS)

                if (idIndex != -1 && nameIndex != -1 && detailsIndex != -1) {
                    val task = TaskListModel().apply {
                        this.id = it.getInt(idIndex)
                        this.name = it.getString(nameIndex)
                        this.details = it.getString(detailsIndex)
                    }
                    taskList.add(task)
                }
            }
        }
        cursor?.close()
        return taskList
    }

    fun addTask(task: TaskListModel): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TASK_NAME, task.name)
            put(TASK_DETAILS, task.details)
        }
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return success != -1L
    }

    fun getTask(id: Int): TaskListModel {
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $id"
        val cursor = db.rawQuery(selectQuery, null)
        var task = TaskListModel() // Create a new instance
        cursor?.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex(ID)
                val nameIndex = it.getColumnIndex(TASK_NAME)
                val detailsIndex = it.getColumnIndex(TASK_DETAILS)

                if (idIndex != -1 && nameIndex != -1 && detailsIndex != -1) {
                    task = TaskListModel().apply {
                        this.id = it.getInt(idIndex)
                        this.name = it.getString(nameIndex)
                        this.details = it.getString(detailsIndex)
                    }
                }
            }
        }
        cursor?.close()
        return task
    }

    fun deleteTask(id: Int): Boolean {
        val db = writableDatabase
        val success = db.delete(TABLE_NAME, "$ID=?", arrayOf(id.toString())).toLong()
        db.close()
        return success != -1L
    }

    fun updateTask(task: TaskListModel): Boolean {
        var success = false
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TASK_NAME, task.name)
            put(TASK_DETAILS, task.details)
        }
        val rowsAffected = db.update(TABLE_NAME, values, "$ID=?", arrayOf(task.id.toString()))
        success = rowsAffected > 0
        db.close()
        return success
    }
}
