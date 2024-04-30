package com.example.darnamob.Accueil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.darnamob.Main.SignIn
import com.example.darnamob.Main.SignUp
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityLoginSignupBinding

class SignInUp : AppCompatActivity() {
    private lateinit var binding : ActivityLoginSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back4.setOnClickListener {

            startActivity(Intent(this, Accueil3::class.java))
            finish()
        }

        binding.signIn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }

        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }
    }
}