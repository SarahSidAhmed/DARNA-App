package com.example.messaging

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context: Context,  val userModelList: MutableList<UserModel>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    constructor(context: MainActivity) : this()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userModel = userModelList[position]
        holder.name.text = userModel.username
        holder.email.text = userModel.useremail
        holder.itemView.setOnClickListener {
            val intent = Intent(context, chat_activity::class.java).apply {
                putExtra("id", userModel.userId)
                putExtra("name", userModel.username)
            }
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return userModelList.size
    }

    fun getUserModelList(): List<UserModel> {
        return userModelList
    }


    fun add(userModel: UserModel) {
        userModelList.add(userModel)
        notifyDataSetChanged()
    }

    fun clear() {
        userModelList.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.username)
        var email: TextView = itemView.findViewById(R.id.useremail)

    }
}