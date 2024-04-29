package com.example.darnamob.Admin

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.R

class ReportedUsersAdapter(private val userlist: List<Artisan>, private val db: DatabaseHelper, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ReportedUsersAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_view_allusers,
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
            holder.domain.visibility = View.GONE
        } else {
            holder.type.text = "Artisan"
            holder.domain.visibility = View.VISIBLE
            holder.domain.text = domaine
        }

        //holder.viewmore.text = "View more"
        holder.viewmore.setOnClickListener {
            // Pass the clicked user's data to onItemClick
            listener.onItemClick(artisan)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView61)
        val username: TextView = itemView.findViewById(R.id.textView81)
        val type: TextView = itemView.findViewById(R.id.textView91)
        val viewmore: TextView = itemView.findViewById(R.id.textView101)
        val domain : TextView = itemView.findViewById(R.id.domain)
        val report : TextView = itemView.findViewById(R.id.report)
    }
    interface OnItemClickListener {
        fun onItemClick(artisan: Artisan)
    }
}