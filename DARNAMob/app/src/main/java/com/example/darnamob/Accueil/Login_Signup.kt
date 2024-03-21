package com.example.darnamob.Accueil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.darnamob.R

class Login_Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)
        val imageview: ImageView = findViewById(R.id.back4)
        imageview.setOnClickListener {
            finish()
        }
    }
}