package com.example.darnamob.Client

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Demande
import com.example.darnamob.Database.data.RendezVousTasks
import com.example.darnamob.R
import org.w3c.dom.Text

class FilteredAdapter (private val listTasks: List<Demande>,context:Context): RecyclerView.Adapter<FilteredAdapter.ViewHolder>(){
 private var db = DatabaseHelper(context)
    private val adapterContext = context



    class ViewHolder(recycler_row: View):RecyclerView.ViewHolder(recycler_row){
        val cardView : CardView = recycler_row.findViewById(R.id.cardViewDemandes)
        val image: ImageView = recycler_row.findViewById(R.id.prest_img)
        val prestText: TextView = recycler_row.findViewById(R.id.prest)
        val prestTime: TextView = recycler_row.findViewById(R.id.prest_time)
        val prestDate = recycler_row.findViewById<TextView>(R.id.day)
        val checkDone = itemView.findViewById<ImageView>(R.id.taskCheckImage)

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

        holder.prestDate.text = currentDemande.date

        val RV = db.getRendezVousClient(currentDemande.id_client)
        var id_Artisan = -1
        var found = false
        var i =0
        while (!found){
            if (RV[i].num_demande == currentOrderNumber){
                found = true
            id_Artisan = RV[i].artisanId}
            else i++
        }

        holder.checkDone.setOnClickListener {
            db.setTaskCompleted(currentOrderNumber)
            db.insertNotifRating(RendezVousTasks(currentDemande.id_client, id_Artisan, currentOrderNumber, true))
            Toast.makeText(adapterContext, "Task set Completed !", Toast.LENGTH_SHORT).show()
        }
    }

}