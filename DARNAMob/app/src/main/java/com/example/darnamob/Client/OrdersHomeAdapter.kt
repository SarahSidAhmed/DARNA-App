package com.example.darnamob.Client

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.Table_Schemas
import com.example.darnamob.Database.data.RendezVousTasks
import com.example.darnamob.R

class OrdersHomeAdapter(private val rendezvous: List<RendezVousTasks>):RecyclerView.Adapter<OrdersHomeAdapter.ViewHolder>() {
    private lateinit var db : DatabaseHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,
            parent, false)
        return OrdersHomeAdapter.ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return rendezvous.size
    }

    val categoryToImageMap = mapOf(
        "Painter" to R.drawable.painting,
        "Plumber" to R.drawable.plumbing,
        "Cleaner" to R.drawable.cleaning,
        "Maconier" to R.drawable.masonry,
        "Electrician" to R.drawable.electricity
    )

    val categoryToBackgroundColorMap = mapOf(
        "Painter" to R.color.paintingOrange,
        "Plumber" to R.color.plumbingBlue,
        "Cleaner" to R.color.cleaningRed,
        "Maconier" to R.color.masoneryGreen,
        "Electrician" to R.color.electricityOrange
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentOrderNumber = rendezvous[position].num_demande
        val currentDemande = db.getDemande(currentOrderNumber)
        // Set the appropriate image resource based on the category
        val imageResource = categoryToImageMap[currentDemande.categorie]
        val backgroundColor = categoryToBackgroundColorMap[currentDemande.categorie]
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.context, backgroundColor ?: R.color.paintingOrange))
        holder.image.setImageResource(imageResource ?: R.drawable.paint) // Use a default image if no match found
        val formattedDuration = currentDemande.hour
         holder.prestText.text = currentDemande.title // Update with appropriate field
         holder.prestTime.text = "$formattedDuration hours" // Display the hour

//        holder.checkDone.setOnClickListener {
//            db.setTaskCompleted(currentOrderNumber)
//            db.insertNotifRating(RendezVousTasks(currentDemande.id_client, ))
//        }
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val cardView : CardView = itemView.findViewById(R.id.cardViewDemandes)
        val image: ImageView = itemView.findViewById(R.id.prest_img)
        val prestText: TextView = itemView.findViewById(R.id.prest)
        val prestTime: TextView = itemView.findViewById(R.id.prest_time)
        val checkDone = itemView.findViewById<ImageView>(R.id.done)

    }

}