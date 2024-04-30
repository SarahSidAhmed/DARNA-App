package com.example.darnamob.Artisant

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.Table_Schemas
import com.example.darnamob.R


class art_view_order : AppCompatActivity() {

    private var Img:Int?=null
    private lateinit var db: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.art_view_order)


        db = DatabaseHelper(this)
        val username = intent.getStringExtra("nomClient")
         val location = intent.getStringExtra("location")
        val details = intent.getStringExtra("details")
        val titre = intent.getStringExtra("titre")
        val material = intent.getBooleanExtra("material",false)
        val addres = intent.getStringExtra("addres")
       // val image = intent.getExtra("image")
        val time = intent.getStringExtra("time")
        val urgent = intent.getBooleanExtra("urgent",false)
        val date = intent.getStringExtra("date")
        val num = intent.getStringExtra("num")




        val usernameTextView = findViewById<TextView>(R.id.clint_name)
       // val profileImageView = findViewById<ImageView>(R.id.profile_photo)
        val detailsTextView = findViewById<TextView>(R.id.detail_text)
        val dateTextView = findViewById<TextView>(R.id.date)
        val timeTextView = findViewById<TextView>(R.id.timee)
        val urgentTextView = findViewById<TextView>(R.id.urgent)
        val materialTextView = findViewById<TextView>(R.id.material)
        val titreTextView = findViewById<TextView>(R.id.prest)
        val addressTextView = findViewById<TextView>(R.id.cite)

        //retrieving the passed data and set it

        usernameTextView.text = username
        detailsTextView.text = details
        dateTextView.text = date
        timeTextView.text = time
        detailsTextView.text = details
        titreTextView.text = titre
        addressTextView.text = addres
        urgentTextView.text= if (urgent) "Urgent: Yes" else "Urgent: No"
        materialTextView.text= if (material) "Material included : Yes" else "Material included : No"
        //for image retrieving
        val image = intent.getByteArrayExtra("image")
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image!!.size)

        val imageView = findViewById<ImageView>(R.id.profile_photo)
        imageView.setImageBitmap(bitmap)






        val idClient = intent.getIntExtra("clientId", -1)
        val idArtisant = intent.getIntExtra("idArtisant", -1)
        val numDemande = intent.getIntExtra("num", -1)




        usernameTextView.text = username


        findViewById<Button>(R.id.confirmButton).setOnClickListener{
            db.insertNotifServiceRequest(idClient, idArtisant, numDemande)
        }

    }

}



