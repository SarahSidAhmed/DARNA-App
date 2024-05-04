package com.example.darnamob.Artisant

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.TypedValue
import com.example.darnamob.Database.data.Membre
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom
import com.example.darnamob.Artisant.Fragments.DiscussionFragment
import com.example.darnamob.Artisant.Fragments.HomeFragment
import com.example.darnamob.R
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Notification
import com.example.darnamob.MainActivity
import com.example.darnamob.databinding.ActivityAccountFragmentBinding


class Notif_adapter(private val notifs: List<Notification>, context: Context) :
    RecyclerView.Adapter<Notif_adapter.MyViewHolder>(){

    private val db: DatabaseHelper = DatabaseHelper(context)
    private val adapterContext = context
    private lateinit var name: String
    private lateinit var image: ByteArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_recycler,
            parent, false)
        return MyViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val members: Membre
        val notif = notifs[position]
        val type = notif.type
        val id = notif.id_sender
        val id2 = notif.id_receiver
        val num = notif.num_demande

        if (type != 0){
        members = db.getMembreByID(id)
            name =members.userName
            image = members.image
        }



        if(type==0){
            holder.detail.text = "Admin sent you a warning"
            holder.detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f )
            holder.image.setImageResource(R.drawable.warning0)
            holder.clientName.visibility=View.GONE
        }

        else if(type==2){
                 holder.detail.text = "Accepted your offer, start messaging to discuss more"
                 val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
                 holder.image.setImageBitmap(bitmap)
                 holder.clientName.text=name

            holder.card.setOnClickListener {
                val intent = Intent(adapterContext, MainActivity::class.java) //change to the profile of the client
                intent.putExtra("clientId", id)
                intent.putExtra("idArtisant", id2)
                intent.putExtra("num", num)

                holder.itemView.context.startActivity(intent)
            }
             }
        else{
            holder.detail.text = "added a review on your profile"
            val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            holder.image.setImageBitmap(bitmap)
            holder.clientName.text=name

            holder.card.setOnClickListener {
                val intent = Intent(adapterContext, HomeFragment::class.java) //change to the profile of the client
                intent.putExtra("clientId", id)
                intent.putExtra("idArtisant", id2)
                intent.putExtra("num", num)

                holder.itemView.context.startActivity(intent)
            }




        }




    }

    override fun getItemCount(): Int {
        return notifs.size
    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //val TextView0: TextView = itemView.findViewById(R.id.admin_mess)
        val detail: TextView = itemView.findViewById(R.id.details2)
        val clientName: TextView = itemView.findViewById(R.id.art_client_username)
        val image: ImageView = itemView.findViewById(R.id.client_pic)
        //val imageImageView0: ImageView = itemView.findViewById(R.id.art_warn)
         val card: CardView = itemView.findViewById(R.id.cardView)


    }


}



