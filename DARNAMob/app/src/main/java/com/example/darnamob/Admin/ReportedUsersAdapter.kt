package com.example.darnamob.Admin

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.R

class ReportedUsersAdapter(private val userlist: List<Artisan>, context: Context) :
    RecyclerView.Adapter<ReportedUsersAdapter.MyViewHolder>() {

    private val db: DatabaseHelper = DatabaseHelper(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.reporteduseritem,
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
        val reports = artisan.membre.ndReports

        // Set user information to views
        val img = artisan.membre.image
        val bitmap = BitmapFactory.decodeByteArray(img, 0, img.size)
        holder.image.setImageBitmap(bitmap)

        holder.username.text = username
        holder.report.text = reports.toString()

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
        holder.viewmore.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.my_custom_blue))
        holder.viewmore.setOnClickListener {
            // Pass the clicked user's data to onItemClick
            val intent = Intent(holder.itemView.context, ActivityProfileClient::class.java ).apply {
                putExtra("id", artisan.membre.id)
            }

            holder.itemView.context.startActivity(intent)
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.profile_pic)
        val username: TextView = itemView.findViewById(R.id.username)
        val type: TextView = itemView.findViewById(R.id.typeMember)
        val viewmore: TextView = itemView.findViewById(R.id.viewMore)
        val domain : TextView = itemView.findViewById(R.id.domain)
        val report : TextView = itemView.findViewById(R.id.reports)
    }

}