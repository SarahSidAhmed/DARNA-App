package com.example.darnamob.Artisant

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.systems.pricingSystem
import org.w3c.dom.Text


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
        val num = intent.getIntExtra("num", -1)

        val demande = db.getDemande(num)




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


        Toast.makeText(this, demande.service, Toast.LENGTH_SHORT).show()
        var price = db.getPrestationPrice(demande.service)

        var hour = time.toString()
        val h1 = hour.get(0)
        val h2 = hour.get(1)

        if (!h2.equals(':')) hour = h1.toString()+h2.toString()
        else  hour = h1.toString()

        val intHour = hour.toInt()
        price = price!! + (pricingSystem().isNight(intHour) + pricingSystem().isFerie(date.toString())+ pricingSystem().getUrgentPrice(urgent))


        findViewById<TextView>(R.id.price).setText("\$ Price: "+ price.toString()+ "DA")



        val idClient = intent.getIntExtra("idClient", -1)
        val idArtisant = intent.getIntExtra("idArtisan", -1)
        val numDemande = intent.getIntExtra("num", -1)




        usernameTextView.text = username


        findViewById<Button>(R.id.confirmButton).setOnClickListener{
            db.insertNotifServiceRequest(idClient, idArtisant, numDemande)
            Toast.makeText(this, "Request for this demand has been sent!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivityArtisant::class.java)
            intent.putExtra("id", idArtisant)
            startActivity(intent)
            finish()
        }

        findViewById<ImageView>(R.id.close).setOnClickListener {
            val intent= Intent(this, MainActivityArtisant::class.java)
            intent.putExtra("id", idArtisant)
            startActivity(intent)
            finish()
        }

    }

}



