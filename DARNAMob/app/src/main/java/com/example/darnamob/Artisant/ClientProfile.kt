package com.example.darnamob.Artisant

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R


private lateinit var db: DatabaseHelper
private var userId = -1

class ClientProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_profile)

        db = DatabaseHelper(this)
        userId = intent.getIntExtra("clientId", -1)

        val member = db.getMembreByID(userId)
        val phone = db.getMemberPhoneByID(userId)
        val email = db.getMemberEmailByID(userId)

        findViewById<TextView>(R.id.phoneNum).setText(member.tel)
        findViewById<TextView>(R.id.email).setText(member.email)
        findViewById<TextView>(R.id.userName).setText(member.userName)
        val image = member.image

        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        findViewById<ImageView>(R.id.profilePic).setImageBitmap(bitmap)



        findViewById<LinearLayout>(R.id.callAction).setOnClickListener {
            checkPermission()
            if (member.tel.isNotEmpty()){
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${member.tel}")
                startActivity(callIntent)
            }
        }

        findViewById<LinearLayout>(R.id.emailAction).setOnClickListener {
            val subject = ""
            val warningMessage = ""


            val intent = Intent(Intent.ACTION_SEND)


            intent.data = Uri.parse("mailto: ")
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, warningMessage)

            try {
                startActivity(Intent.createChooser(intent, "Send Email"))
            }catch (e: Exception){
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }

        }

        findViewById<ImageView>(R.id.report).setOnClickListener {
            db.reportUser(userId)
            Toast.makeText(this, "User reported!", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkPermission(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 101)
        }
    }
}