package com.example.darnamob.Client

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityAccountClientSettingsBinding
import java.io.ByteArrayOutputStream

private lateinit var binding : ActivityAccountClientSettingsBinding
private lateinit var db: DatabaseHelper

class AccountClientSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountClientSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseHelper(this)

        val userID = intent.getIntExtra("id", -1)



        //getting the data
        val membre = db.getMembreByID(userID)

        //setting the data
        binding.artAddress.setText(membre.address)
        binding.artPhone.setText(membre.tel)


        val image = membre.image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)

        binding.artprofilpic.setImageBitmap(bitmap)





        //you need to add the thing to get the image from their gallery and affect it to the
        //image view so that it changes



        binding.save.setOnClickListener {

            val newAdr = binding.artAddress.text.toString().trim()
            val newphone = binding.artPhone.text.toString().trim()

            //updating the data in the database
            if (image.isNotEmpty()){
            db.editProfileMember(userID, newphone, newAdr, image)

            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(this, "Infos can not be empty, check again", Toast.LENGTH_SHORT).show()
        }
    }
}