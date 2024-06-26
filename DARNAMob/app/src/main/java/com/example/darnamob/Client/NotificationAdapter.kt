package com.example.darnamob.Client

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Parcel
import android.os.Parcelable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Artisant.Fragments.DiscussionFragment
import com.example.darnamob.Artisant.Fragments.HomeFragment
import com.example.darnamob.Artisant.Notif_adapter
import com.example.darnamob.Artisant.Notifications
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Membre
import com.example.darnamob.Database.data.Notification
import com.example.darnamob.R


class NotificationAdapter(private val notifs: List<Notification>, context: Context) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private val db: DatabaseHelper = DatabaseHelper(context)
    private val adapterContext = context
    private lateinit var name: String
    private lateinit var image: ByteArray


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val detail: TextView = itemView.findViewById(R.id.details)
        val clientName: TextView = itemView.findViewById(R.id.art_client_username)
        val image: ImageView = itemView.findViewById(R.id.artisan_pic)
        val card: CardView = itemView.findViewById(R.id.notif_item_artisan)
        val confirm : Button = itemView.findViewById(R.id.confirm_button)
        val delete : Button = itemView.findViewById(R.id.delete_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_recycler_client,
            parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notifs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val members: Membre
        val notif = notifs[position]
        val type = notif.type
        val id = notif.id_receiver
        val num = notif.num_demande
        val idSender = notif.id_sender



        if(type==0){
            holder.detail.text = "Admin sent you a warning"
            //holder.detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f )
            holder.image.setImageResource(R.drawable.warning0)
            holder.clientName.visibility=View.GONE
            holder.confirm.visibility = View.GONE
            holder.delete.visibility = View.GONE

        }


        else if(type==1){
            members = db.getMembreByID(idSender)
             name =members.userName
             image = members.image

            holder.confirm.visibility = View.VISIBLE
            holder.delete.visibility = View.VISIBLE
            holder.detail.text = "Accepted your order, confirm it to start messaging to discuss more"
            val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            holder.image.setImageBitmap(bitmap)
            holder.clientName.text=name
            holder.confirm.setOnClickListener{
                db.confirm(Notification(idSender, id, num, 2))
                db.deleteNotif(Notification(id, idSender, num, 2))
                val intent = Intent(adapterContext, MainActivityClient::class.java)
                intent.putExtra("id", id)
                holder.itemView.context.startActivity(intent)
            }
            holder.delete.setOnClickListener{
                db.deleteNotif(Notification(id, idSender, num, 2))
                val intent = Intent(adapterContext, MainActivityClient::class.java)
                intent.putExtra("id", id)
                holder.itemView.context.startActivity(intent)
            }

        }
        else if (type == 3){

            members = db.getMembreByID(idSender)
            name =members.userName
            image = members.image
            holder.detail.text = "How was your latest order, Rate it now"
            val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            holder.image.setImageBitmap(bitmap)
            holder.clientName.text=name

            holder.confirm.visibility = View.GONE
            holder.delete.visibility = View.GONE

            //holder.detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f )
            holder.card.setOnClickListener {
                val intent = Intent(adapterContext, ArtisanProfileComments::class.java)
                intent.putExtra("idArtisan", idSender)
                intent.putExtra("id", id)
                holder.itemView.context.startActivity(intent)
            }

        }

    }


}