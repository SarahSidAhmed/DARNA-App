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
import androidx.core.content.ContextCompat
import com.example.darnamob.Artisant.Fragments.DiscussionFragment
import com.example.darnamob.Artisant.Fragments.HomeFragment
import com.example.darnamob.Client.OrdersHomeAdapter
import com.example.darnamob.R
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Notification
import com.example.darnamob.Database.data.RendezVousTasks
import com.example.darnamob.MainActivity
import com.example.darnamob.databinding.ActivityAccountFragmentBinding



class CalendrierAdapter(private val rendezvousList: List<RendezVousTasks>,context:Context):
    RecyclerView.Adapter<CalendrierAdapter.ViewHolder>() {

    private lateinit var db : DatabaseHelper
    private val adapterContext = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return rendezvousList.size
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
        val currentOrderNumber = rendezvousList[position].num_demande
        val currentDemande = db.getDemande(currentOrderNumber)
        holder.prestText.text = currentDemande.title
        holder.prestTime.text = currentDemande.hour
        val imageResource = categoryToImageMap[currentDemande.categorie]
        val backgroundColor = categoryToBackgroundColorMap[currentDemande.categorie]
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.context, backgroundColor ?: R.color.paintingOrange))
        holder.image.setImageResource(imageResource ?: R.drawable.paint) // Use a default image if no match found
        val formattedDuration = currentDemande.hour
        holder.prestText.text = currentDemande.title // Update with appropriate field
        holder.prestTime.text = "$formattedDuration hours" // Display the hour
    }



    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.prest_img)
        val prestText: TextView = itemView.findViewById(R.id.prest)
        val prestTime: TextView = itemView.findViewById(R.id.prest_time)
        val cardView : CardView = itemView.findViewById(R.id.cardViewDemandes)


    }

}


