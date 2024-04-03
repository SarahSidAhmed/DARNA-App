package com.example.darnamob.Client

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Toast
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityAccountClientSettingsBinding

private lateinit var binding : ActivityAccountClientSettingsBinding
private lateinit var db: DatabaseHelper

class AccountClientSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountClientSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseHelper(this)
        val userID = 2 // intent.getIntExtra("id", -1)

        val membre = db.getMembreByID(userID)

        binding.artAddress.setText(membre.address)
        binding.artPhone.setText(membre.tel)
        Toast.makeText(this, "HERE", Toast.LENGTH_SHORT).show()

        var image = membre.image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        binding.artprofilpic.setImageBitmap(bitmap)



        val newAdr = binding.artAddress.text.toString().trim()
        val newphone = binding.artPhone.text.toString().trim()



        //here we need to add how to get their image from the gallery
        //should be replaced with the new image if they changed it

        binding.save.setOnClickListener {

            //updating the data in the database
            db.editProfileMember(userID, newphone, newAdr, image)

            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivityClient::class.java))
            finish()
        }
    }
}