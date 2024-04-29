package com.example.darnamob.Accueil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.darnamob.R

class Accueil3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil3)

        val button: Button = findViewById(R.id.button3)

        button.setOnClickListener {
            val intent = Intent(this, SignInUp::class.java)
            startActivity(intent)
            finish()
        }

        val imageview: ImageView = findViewById(R.id.back3)

        imageview.setOnClickListener {
            val intent = Intent(this, Accueil2::class.java)
            startActivity(intent)
            finish()
        }

        val textview: TextView = findViewById(R.id.skip3)

        textview.setOnClickListener {
            val intent = Intent(this,SignInUp::class.java)
            startActivity(intent)
            finish()
        }
    }
}