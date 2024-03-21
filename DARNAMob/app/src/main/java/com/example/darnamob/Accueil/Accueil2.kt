package com.example.darnamob.Accueil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.R
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Accueil2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil2)
        val button: Button = findViewById(R.id.button2)
        button.setOnClickListener {
            val intent = Intent(this, Accueil3::class.java)
            startActivity(intent)
        }
        val imageview: ImageView = findViewById(R.id.back2)
        imageview.setOnClickListener {
            finish()
        }

        val textview: TextView = findViewById(R.id.skip2)
        textview.setOnClickListener {
            val intent = Intent(this,Login_Signup::class.java)
            startActivity(intent)
        }
    }
}