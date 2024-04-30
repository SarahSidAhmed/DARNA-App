package com.example.darnamob.Client

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.RendezVousTasks
import com.example.darnamob.R

class OrdersHomeAdapter(private val rendezvousList: ArrayList<RendezVousTasks>):RecyclerView.Adapter<OrdersHomeAdapter.ViewHolder>() {
    private lateinit var db : DatabaseHelper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return rendezvousList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentOrderNumber = rendezvousList[position].num_demande
        val currentDemande = db.getDemande(currentOrderNumber)
         holder.prestText.text = currentDemande.title // Update with appropriate field
         holder.prestTime.text = currentDemande.hour // Display the hour
    }


    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.prest_img)
        val prestText: TextView = itemView.findViewById(R.id.prest)
        val prestTime: TextView = itemView.findViewById(R.id.prest_time)

    }


}