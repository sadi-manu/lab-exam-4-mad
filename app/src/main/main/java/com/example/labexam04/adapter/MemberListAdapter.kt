package com.example.labexam04.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.AddMember
import com.example.labexam04.MainActivity

import com.example.labexam04.R
import com.example.labexam04.model.MemberListModel

class MemberListAdapter(private val memberList: List<MemberListModel>, private val context: Context) :
    RecyclerView.Adapter<MemberListAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.recycler_member_list, parent, false)
        return MemberViewHolder(view)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = memberList[position]
        holder.mid.text = member.mid.toString()
        holder.mname.text = member.mname


        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, AddMember::class.java).apply {
                putExtra("Mode", "E")
                putExtra("Id", member.mid)
            }

            context.startActivity(intent)
        }
    }

    inner class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mid: TextView = view.findViewById(R.id.member_id)
        val mname: TextView = view.findViewById(R.id.member_name)
        val btnEdit: Button = view.findViewById(R.id.btn_edit_member)
    }
}
