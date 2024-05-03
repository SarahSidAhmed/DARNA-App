package com.example.darnamob.Client

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Demande
import com.example.darnamob.Database.data.RendezVousTasks
import com.example.darnamob.R

class FilteredAdapter (private val listTasks: List<Demande>,context:Context): RecyclerView.Adapter<FilteredAdapter.ViewHolder>(){
 lateinit var db:DatabaseHelper



    class ViewHolder(recycler_row: View):RecyclerView.ViewHolder(recycler_row){
        val cardView : CardView = recycler_row.findViewById(R.id.cardViewDemandes)
        val image: ImageView = recycler_row.findViewById(R.id.prest_img)
        val prestText: TextView = recycler_row.findViewById(R.id.prest)
        val prestTime: TextView = recycler_row.findViewById(R.id.prest_time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return FilteredAdapter.ViewHolder(v)

    }


    override fun getItemCount(): Int {
       return listTasks.size
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
        val currentOrderNumber = listTasks[position].num_demande
        val currentDemande = db.getDemande(currentOrderNumber)
        val imageResource = categoryToImageMap[currentDemande.categorie]
        val backgroundColor = categoryToBackgroundColorMap[currentDemande.categorie]
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.context, backgroundColor ?: R.color.paintingOrange))
        holder.image.setImageResource(imageResource ?: R.drawable.paint) // Use a default image if no match found
        val formattedDuration = currentDemande.hour
        holder.prestText.text = currentDemande.title // Update with appropriate field
        holder.prestTime.text = "$formattedDuration hours" // Display the hour

    }

}