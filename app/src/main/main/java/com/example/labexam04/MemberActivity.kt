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
import com.example.labexam04.adapter.MemberListAdapter

import com.example.labexam04.database.DatabaseHelper
import com.example.labexam04.model.MemberListModel

class MemberActivity : AppCompatActivity() {
    private lateinit var recyclerMember: RecyclerView
    private lateinit var btnAdd: Button

    private var memberListAdapter: MemberListAdapter? = null
    private var dbHandler: DatabaseHelper? = null
    private var memberList: List<MemberListModel> = ArrayList()
    private var linearLayoutManager: LinearLayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_member)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.member)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerMember = findViewById(R.id.mem_list)
        btnAdd = findViewById(R.id.ft_add_memebers)


        dbHandler = DatabaseHelper(this)
        fetchList()

        btnAdd.setOnClickListener {
            val intent = Intent(applicationContext, AddMember::class.java)
            startActivity(intent)
        }

    }
    private fun fetchList() {
        memberList = dbHandler?.getAllMembers() ?: emptyList()
        memberListAdapter = MemberListAdapter(memberList, this) // Change applicationContext to this
        linearLayoutManager = LinearLayoutManager(this) // Change applicationContext to this
        recyclerMember.layoutManager = linearLayoutManager
        recyclerMember.adapter = memberListAdapter
        memberListAdapter?.notifyDataSetChanged()
    }
}