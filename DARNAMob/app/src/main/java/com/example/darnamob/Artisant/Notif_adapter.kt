package com.example.darnamob.Artisant

import android.graphics.BitmapFactory
import com.example.darnamob.Database.data.Membre
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.example.darnamob.R
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Notification


class Notif_adapter(private val notifs: List<Notification>, private val db: DatabaseHelper, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<Notif_adapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Notif_adapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_recycler,
            parent, false)
        return MyViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: Notif_adapter.MyViewHolder, position: Int) {
        val members:Membre
        val notif = notifs[position]
        val type = notif.type
        val id = notif.id_sender
        members = db.getMembreByID(id)
        val name =members.userName
        val image = members.image


        if(type==0){
            holder.TextView2.text = "Admin sent you a warning"
            holder.imageImageView.setImageResource(R.drawable.warning0)
            holder.client_nameTextView.visibility=View.GONE
             if(type==2){
                 holder.TextView2.text = "accepted your offer, start messaging to discuss more"
                 val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
                 holder.imageImageView.setImageBitmap(bitmap)
                 holder.client_nameTextView.text=name
             }

        }else{

            holder.TextView2.text = "added a review on your profile"
            val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            holder.imageImageView.setImageBitmap(bitmap)
            holder.client_nameTextView.text=name
        }




    }

    override fun getItemCount(): Int {
        return notifs.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //val TextView0: TextView = itemView.findViewById(R.id.admin_mess)
        val TextView2: TextView = itemView.findViewById(R.id.details2)
        val client_nameTextView: TextView = itemView.findViewById(R.id.art_client_username)
        val imageImageView: ImageView = itemView.findViewById(R.id.client_pic)
        //val imageImageView0: ImageView = itemView.findViewById(R.id.art_warn)


    }
    interface OnItemClickListener {
        fun onItemClick(notif: Notification)
    }


}



