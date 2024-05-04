package com.example.darnamob.Artisant

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import com.example.darnamob.Database.data.Demande
import com.example.darnamob.Database.data.Membre
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.example.darnamob.R
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Comment
import com.example.darnamob.MainActivity


class CommentAdapter (private val comments: List<Comment>, context: Context) : RecyclerView.Adapter<CommentAdapter.MyViewHolder>(){

    private val db: DatabaseHelper = DatabaseHelper(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.art_comments,
            parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val members:Membre
        val comment = comments[position]
        val id_client = comment.commenterID
        val id_artisant =comment.artisanId
        members = db.getMembreByID(id_client)
        val name =members.userName
        val text = comment.commentText
        val rating = comment.rating
        val image = members.image

        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        holder.imageImageView.setImageBitmap(bitmap)
        holder.client_nameTextView.text = name
        holder.ratingTextView.text = rating.toString()
        holder.detailsTextView.text = text


    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val client_nameTextView: TextView = itemView.findViewById(R.id.client_com_name)
        val ratingTextView: TextView = itemView.findViewById(R.id.num_rating)
        val detailsTextView: TextView = itemView.findViewById(R.id.client_com_details)
        val imageImageView: ImageView = itemView.findViewById(R.id.client_com_pic)

    }



}