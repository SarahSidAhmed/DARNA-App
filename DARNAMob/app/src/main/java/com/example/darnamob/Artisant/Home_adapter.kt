package com.example.darnamob.Artisant

import android.content.Intent
import android.graphics.BitmapFactory
import com.example.darnamob.Database.data.Demande
import com.example.darnamob.Database.data.Membre
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.darnamob.R
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Artisant.Fragments.HomeFragment
import java.lang.reflect.Member
import com.google.android.material.imageview.ShapeableImageView
import com.example.darnamob.databinding.ActivityHomeFragmentBinding




class Home_adapter(private val demandes: List<Demande>, private val db: DatabaseHelper, private val listener: OnItemClickListener) :
RecyclerView.Adapter<Home_adapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Home_adapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_home_fragment,
            parent, false)
        return MyViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: Home_adapter.MyViewHolder, position: Int) {
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

        holder.itemView.setOnClickListener(){

            val intent = Intent(holder.itemView.context,art_view_order::class.java)

            intent.putExtra( "nomClient",name)
            intent.putExtra( "location",location)
            intent.putExtra( "details",details)
            intent.putExtra( "titre",titre)
            intent.putExtra( "material",material)
            intent.putExtra( "addres",addres)
            intent.putExtra( "image",image)
            intent.putExtra( "time",time)
            intent.putExtra( "urgent",urgent)
            intent.putExtra( "date",date)
            intent.putExtra( "num",num)


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

        val prestationTextView: TextView = itemView.findViewById(R.id.prestation)
        val client_nameTextView: TextView = itemView.findViewById(R.id.client_name)
        val timeTextView: TextView = itemView.findViewById(R.id.time)
       val locationTextView: TextView = itemView.findViewById(R.id.location)
        val detailsTextView: TextView = itemView.findViewById(R.id.details)
        val imageImageView: ImageView = itemView.findViewById(R.id.client_pic)
        val imageImageView1: ImageView = itemView.findViewById(R.id.urgent)



    }
    interface OnItemClickListener {
        fun onItemClick(demande: Demande)
    }


}
