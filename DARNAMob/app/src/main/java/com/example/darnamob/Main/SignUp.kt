package com.example.darnamob.Main

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.darnamob.Accueil.SignInUp
import com.example.darnamob.Client.MainActivityClient
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Membre
import com.example.darnamob.databinding.ActivitySignUpBinding
import com.example.darnamob.imageFromDrawableToByteArray
import java.io.ByteArrayOutputStream

private lateinit var binding : ActivitySignUpBinding
private lateinit var db : DatabaseHelper

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        binding.signUpButtom.setOnClickListener {
            Signup()
        }

        binding.alreadyExist.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }

        binding.back.setOnClickListener {
            startActivity(Intent(this, SignInUp::class.java))
            finish()
        }


    }

    private fun Signup() {
        val userName = binding.fullNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirm = binding.passwordConfirmEditText.text.toString()


        if (email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty() && userName.isNotEmpty()) {
            val drawable_name = "initprofile"
            val resId = resources.getIdentifier(drawable_name, "drawable", "com.example.darnamob")

            if (resId != 0) {
                val image =
                    imageFromDrawableToByteArray(this, resId) //converting image to ByteArray

                val checkExist = db.checkEmail(email)
                if (checkExist) Toast.makeText(
                    this,
                    "User already exists, Sign in.",
                    Toast.LENGTH_SHORT
                ).show()
                else {//user doesn't exist
                    if (password == confirm) {
                        db.insertMembre(
                            Membre(0, phone, "", email, password, userName, image, 0),
                            true
                        )
                        val intent = Intent(this, MainActivityClient::class.java)
                        intent.putExtra("id", db.getUserID(email))
                        Toast.makeText(this, "${db.getUserID(email)}", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    }
                    else Toast.makeText(this, "Check your password", Toast.LENGTH_SHORT).show()
                }
            } else Toast.makeText(this, "Something happened. Try again.", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, "Fields can not be empty. Check you information again", Toast.LENGTH_SHORT).show()

    }

}