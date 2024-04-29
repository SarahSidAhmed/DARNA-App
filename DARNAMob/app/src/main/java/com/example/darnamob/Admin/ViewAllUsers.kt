package com.example.darnamob.Admin

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.darnamob.R
import com.example.darnamob.Database.DatabaseHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.databinding.ActivityViewAllusersBinding
import com.google.android.gms.common.api.Api
import com.google.android.material.imageview.ShapeableImageView
import java.lang.reflect.Member


class ViewAllUsers(private val userlist: List<Artisan>, private val db: DatabaseHelper) :
    RecyclerView.Adapter<ViewAllUsers.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewuser,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val artisan = userlist[position]
        val email = artisan.membre.email
        val username = artisan.membre.userName
        val isClient = db.checkIfClient(email)
        val domaine = artisan.domain
        // Set user information to views
        val img = artisan.membre.image
        val bitmap = BitmapFactory.decodeByteArray(img, 0, img.size)
        holder.image.setImageBitmap(bitmap)

        holder.username.text = username

        if (isClient) {
            holder.type.text = "client"
            holder.type.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            holder.domain.visibility = View.GONE
        } else {
            holder.type.text = "Artisan"
            holder.type.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.vert))
            holder.domain.visibility = View.VISIBLE
            holder.domain.text = domaine
        }

        holder.viewmore.text = "View more"
        holder.viewmore.setOnClickListener {
            // Pass the clicked user's data to onItemClick
        }
    }

    class MyViewHolder(viewuser: View) : RecyclerView.ViewHolder(viewuser) {
        val image: ImageView = viewuser.findViewById(R.id.profilepic)
        val username: TextView = viewuser.findViewById(R.id.username)
        val type: TextView = viewuser.findViewById(R.id.type)
        val viewmore: TextView = viewuser.findViewById(R.id.viewmore)
        val domain : TextView = viewuser.findViewById(R.id.domaine)
    }

}