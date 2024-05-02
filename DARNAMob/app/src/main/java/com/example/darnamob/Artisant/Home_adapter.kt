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
import com.example.darnamob.Artisant.Fragments.HomeFragment


class Home_adapter(private val demandes: List<Demande>, context: Context) : RecyclerView.Adapter<Home_adapter.MyViewHolder>(){

    private val db: DatabaseHelper = DatabaseHelper(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.demandeitem,
            parent, false)
        return MyViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val members:Membre
        val demande = demandes[position]
        val titre = demande.title
        val num = demande.num_demande
        val id = demande.id_client
        members = db.getMembreByID(id)
        val name =members.userName
        val time = demande.hour
        val location = demande.region
        val image = members.image
        val details = demande.description
        val urgent =demande.urgent
        val date =demande.date
        val addres = demande.address
        val material =demande.material
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        holder.imageImageView.setImageBitmap(bitmap)
        holder.client_nameTextView.text = name
        holder.prestationTextView.text = titre
        holder.timeTextView.text = time
        holder.locationTextView.text = location
        holder.detailsTextView.text = details

        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView.context,art_view_order::class.java).apply {

                putExtra( "nomClient",name)
                putExtra( "location",location)
                putExtra( "details",details)
                putExtra( "titre",titre)
                putExtra( "material",material)
                putExtra( "addres",addres)
                putExtra( "image",image)
                putExtra( "time",time)
                putExtra( "urgent",urgent)
                putExtra( "date",date)
                putExtra( "num",num)
            }

            holder.itemView.context.startActivity(intent)

        }


        if(urgent){
            holder.imageImageView1.visibility=View.VISIBLE
        }else{
            holder.imageImageView1.visibility=View.GONE
        }



    }

    override fun getItemCount(): Int {
        return demandes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val prestationTextView: TextView = itemView.findViewById(R.id.art_prest)
        val client_nameTextView: TextView = itemView.findViewById(R.id.art_client_name)
        val timeTextView: TextView = itemView.findViewById(R.id.time)
       val locationTextView: TextView = itemView.findViewById(R.id.location)
        val detailsTextView: TextView = itemView.findViewById(R.id.details)
        val imageImageView: ImageView = itemView.findViewById(R.id.client_pic)
        val imageImageView1: ImageView = itemView.findViewById(R.id.urgent)

    }



}
