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
import com.example.labexam04.model.MemberListModel


class AddMember : AppCompatActivity() {
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var etmid: EditText
    private lateinit var etmName: EditText
    private var dbHandler: DatabaseHelper? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_member)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.member)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnSave = findViewById(R.id.btn_save_mem)
        btnDelete = findViewById(R.id.btn_del_mem)
        etmid = findViewById(R.id.et_mid)
        etmName = findViewById(R.id.et_mname)
        dbHandler = DatabaseHelper(this)

        if (intent != null && intent.getStringExtra("Mode") == "E") {
            // Update data
            isEditMode = true
            btnSave.text = "Update data"
            btnDelete.visibility = View.VISIBLE
            val member: MemberListModel = dbHandler!!.getMember(intent.getIntExtra("Id", 0))
            etmid.setText(member.mid.toString())
            etmName.setText(member.mname)

        } else {
            // Insert new data
            isEditMode = false
            btnSave.text = "Save data"
            btnDelete.visibility = View.GONE
        }
        btnSave.setOnClickListener {
            var success = false
            val member = MemberListModel().apply {
                if (isEditMode) {
                    mid = intent.getIntExtra("Id", 0)
                }
                mid = etmid.text.toString().toIntOrNull() ?: 0
                mname = etmName.text.toString()
            }
            success = if (isEditMode) {
                dbHandler?.updateMember(member) ?: false
            } else {
                dbHandler?.addMember(member) ?: false
            }

            if (success) {
                val intent = Intent(applicationContext, MemberActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext, "Something went wrong!!", Toast.LENGTH_LONG).show()
            }
        }

        btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click Yes if you want to delete the member")
                .setPositiveButton("YES") { dialog, _ ->
                    val success = dbHandler?.deleteMember(intent.getIntExtra("Id", 0)) ?: false
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