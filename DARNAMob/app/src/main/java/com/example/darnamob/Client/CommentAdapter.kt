package com.example.darnamob.Client

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Comment
import com.example.darnamob.Database.data.Membre
import com.example.darnamob.R


class CommentAdapter(private val commentsList: List<Comment>, context: Context): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {


    private val db: DatabaseHelper = DatabaseHelper(context)
    private val adapterContext = context
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val description: TextView = itemView.findViewById(R.id.description)
        val clientName: TextView = itemView.findViewById(R.id.art_username)
        val image: ImageView = itemView.findViewById(R.id.art_pic)
        //val card: CardView = itemView.findViewById(R.id.notif_item_artisan)
        val star : ImageView = itemView.findViewById(R.id.star)
        val rating : Button = itemView.findViewById(R.id.rating)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.comment_recycler_row,parent,false)
        return CommentAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val members: Membre
        val comment = commentsList[position]
        val id = comment.commenterID
        members = db.getMembreByID(id)
        val name =members.userName
        val image = members.image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        holder.image.setImageBitmap(bitmap)
        val text = comment.commentText
        holder.clientName.text=name
        holder.description.text=text
        holder.rating.text=text
        holder.star.setImageResource(R.drawable.yellowstar)
    }

}