

// DatabaseHelper.kt
package com.example.labexam04.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.labexam04.model.MemberListModel
import com.example.labexam04.model.TaskListModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "task"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "tasklist"
        private const val TABLE_NAME2 = "memberlist"
        private const val ID = "id"
        private const val TASK_ID = "taskid"
        private const val TASK_NAME = "taskname" // Define only once
        private const val TASK_DETAILS = "taskdetails"
        private const val TASK_SUPERVISOR= "tasksupervisor"
        private const val TASK_DURATION= "taskduration"
        private const val TASK_PRIORITY = "taskpriority"
        private const val MEMBER_ID = "memberid"
        private const val MEMBER_NAME = "membername"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $TASK_ID TEXT, $TASK_NAME TEXT, $TASK_DETAILS TEXT, $TASK_SUPERVISOR TEXT , $TASK_DURATION TEXT , $TASK_PRIORITY TEXT, $MEMBER_ID INTEGER,  FOREIGN KEY ($MEMBER_ID) REFERENCES $TABLE_NAME2($MEMBER_ID));"
        val CREATE_TABLE2 =
            "CREATE TABLE $TABLE_NAME2 ($MEMBER_ID INTEGER PRIMARY KEY, $MEMBER_NAME TEXT);"
        db?.execSQL(CREATE_TABLE)
        db?.execSQL(CREATE_TABLE2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        val DROP_TABLE2 = "DROP TABLE IF EXISTS $TABLE_NAME2"
        db?.execSQL(DROP_TABLE2)
        onCreate(db)
    }

    fun getAllTasks(): List<TaskListModel> {
        val taskList = ArrayList<TaskListModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex(ID)
                val taskIdIndex = it.getColumnIndex(TASK_ID)
                val nameIndex = it.getColumnIndex(TASK_NAME)
                val detailsIndex = it.getColumnIndex(TASK_DETAILS)
                val supervisorIndex = it.getColumnIndex(TASK_SUPERVISOR)
                val durationIndex = it.getColumnIndex(TASK_DURATION)
                val priorityIndex = it.getColumnIndex(TASK_PRIORITY)

                do {
                    val task = TaskListModel().apply {
                        this.id = it.getInt(idIndex)
                        this.tid = it.getString(taskIdIndex)
                        this.name = it.getString(nameIndex)
                        this.details = it.getString(detailsIndex)
                        this.supervisor = it.getString(supervisorIndex)
                        this.duration = it.getString(durationIndex)
                        this.priority = it.getString(priorityIndex)
                    }
                    taskList.add(task)
                } while (it.moveToNext())
            }
        }
        cursor?.close()
        return taskList
    }




    fun addTask(task: TaskListModel): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TASK_ID, task.tid)
            put(TASK_NAME, task.name)
            put(TASK_DETAILS, task.details)
            put(TASK_SUPERVISOR, task.supervisor)
            put(TASK_DURATION, task.duration)
            put(TASK_PRIORITY, task.priority)
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
                val tidIndex = it.getColumnIndex(TASK_ID)
                val nameIndex = it.getColumnIndex(TASK_NAME)
                val detailsIndex = it.getColumnIndex(TASK_DETAILS)
                val supervisorIndex = it.getColumnIndex(TASK_SUPERVISOR)
                val durationIndex = it.getColumnIndex(TASK_DURATION)
                val priorityIndex = it.getColumnIndex(TASK_PRIORITY)

                if (idIndex != -1 && nameIndex != -1 && detailsIndex != -1) {
                    task = TaskListModel().apply {
                        this.id = it.getInt(idIndex)
                        this.tid = it.getString(tidIndex)
                        this.name = it.getString(nameIndex)
                        this.details = it.getString(detailsIndex)
                        this.supervisor = it.getString(supervisorIndex)
                        this.duration = it.getString(durationIndex)
                        this.priority = it.getString(priorityIndex)
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
            put(TASK_ID, task.tid)
            put(TASK_NAME, task.name)
            put(TASK_DETAILS, task.details)
            put(TASK_SUPERVISOR, task.supervisor)
            put(TASK_DURATION, task.duration)
            put(TASK_PRIORITY, task.priority)
        }
        val rowsAffected = db.update(TABLE_NAME, values, "$ID=?", arrayOf(task.id.toString()))
        success = rowsAffected > 0
        db.close()
        return success
    }

    fun getAllMembers(): List<MemberListModel> {
        val memberList = ArrayList<MemberListModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME2"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val midIndex = it.getColumnIndex(MEMBER_ID)
                val MnameIndex = it.getColumnIndex(MEMBER_NAME)

                do {
                    val member = MemberListModel().apply {
                        this.mid = it.getInt(midIndex)
                        this.mname = it.getString(MnameIndex)

                    }
                    memberList.add(member)
                } while (it.moveToNext())
            }
        }
        cursor?.close()
        return memberList
    }
    fun addMember(member: MemberListModel): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(MEMBER_ID, member.mid)
            put(MEMBER_NAME, member.mname)

        }
        val success = db.insert(TABLE_NAME2, null, values)
        db.close()
        return success != -1L
    }

    fun getMember(mid: Int): MemberListModel {
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME2 WHERE $MEMBER_ID= $mid"
        val cursor = db.rawQuery(selectQuery, null)
        var member = MemberListModel() // Create a new instance
        cursor?.use {
            if (it.moveToFirst()) {
                val midIndex = it.getColumnIndex(MEMBER_ID)
                val mnameIndex = it.getColumnIndex(MEMBER_NAME)


                if (midIndex != -1 && mnameIndex != -1 ) {
                    member = MemberListModel().apply {
                        this.mid = it.getInt(midIndex)
                        this.mname = it.getString(mnameIndex)

                    }
                }
            }
        }
        cursor?.close()
        return member
    }

    fun deleteMember(mid: Int): Boolean {
        val db = writableDatabase
        val success = db.delete(TABLE_NAME2, "$ID=?", arrayOf(mid.toString())).toLong()
        db.close()
        return success != -1L
    }

    fun updateMember(member: MemberListModel): Boolean {
        var success = false
        val db = writableDatabase
        val values = ContentValues().apply {
            put(MEMBER_ID, member.mid)
            put(MEMBER_NAME, member.mname)

        }
        val rowsAffected = db.update(TABLE_NAME2, values, "$MEMBER_ID=?", arrayOf(member.mid.toString()))

        success = rowsAffected > 0
        db.close()
        return success
    }
}
