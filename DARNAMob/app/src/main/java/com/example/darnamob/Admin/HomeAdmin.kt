package com.example.darnamob.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.R
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.widget.ImageView
import com.example.darnamob.Accueil.Accueil1
import com.example.darnamob.Admin.ActivityViewAllusers
import com.example.darnamob.Admin.CreateAccount


class HomeAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val button: Button = findViewById(R.id.button)
        val button2: Button = findViewById(R.id.button2)
        button.setOnClickListener {
            val intent = Intent(this, ActivityViewAllusers::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, CreateAccount::class.java)
           startActivity(intent)
        }
        val image : ImageView = findViewById(R.id.logout)
        image.setOnClickListener {
            val intent = Intent(this, Accueil1::class.java)
            startActivity(intent)
        }
    }
}