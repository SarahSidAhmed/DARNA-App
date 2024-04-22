package com.example.darnamob.Admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.darnamob.R
import com.example.darnamob.Database.DatabaseHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import java.lang.reflect.Member

//private lateinit var db: DatabaseHelper
class ViewAllUsers (private val userlist: ArrayList<Member>) :
    RecyclerView.Adapter<ViewAllUsers.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_viewallusers,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userlist[position]
        holder.image.setImageResource(currentItem.modifiers)
        holder.username.text = currentItem.name
        //holder.type.text = db.CheckIfClient(email)
        //holder.viewmore.text = currentItem.toString("view more")
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image: ShapeableImageView = itemView.findViewById(R.id.imageView61)
        val username: TextView = itemView.findViewById(R.id.textView81)
        val type: TextView = itemView.findViewById(R.id.textView91)
        val viewmore: TextView = itemView.findViewById(R.id.textView101)
    }

}

private fun ShapeableImageView.setImageResource(image: ByteArray) {

}
