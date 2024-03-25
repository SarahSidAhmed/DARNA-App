package com.example.darnamob.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.darnamob.Accueil.Accueil1
import com.example.darnamob.Accueil.SplashScreen
import com.example.darnamob.R

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        Handler().postDelayed({
            // Start the new activity
            val intent = Intent(this, Accueil1::class.java)
            startActivity(intent)
            // Finish current activity
            finish()
        }, 5000)

    }
}