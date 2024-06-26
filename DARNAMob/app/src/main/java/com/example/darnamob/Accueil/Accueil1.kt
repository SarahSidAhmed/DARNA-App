package com.example.darnamob.Accueil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.R
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.widget.ImageView
import com.example.darnamob.Main.Main


class Accueil1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil1)

        val button: Button = findViewById(R.id.button1)


        button.setOnClickListener {
            val intent = Intent(this, Accueil2::class.java)
            startActivity(intent)
            finish()
        }

        val textview: TextView = findViewById(R.id.skip1)

        textview.setOnClickListener {
            val intent = Intent(this,SignInUp::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<ImageView>(R.id.back1).setOnClickListener {
            val intent = Intent(this, Main::class.java)
            startActivity(intent)
            finish()
        }
    }
}
