package com.example.messaging

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.messaging.MainActivity
import com.example.messaging.R
import com.example.messaging.Signup_activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class SignIn_activity : AppCompatActivity() {
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var signinBtn: TextView
    private lateinit var signupBtn: TextView
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        userEmail = findViewById(R.id.email)
        userPassword = findViewById(R.id.password)
        signinBtn = findViewById(R.id.login)
        signupBtn = findViewById(R.id.signup)

        signinBtn.setOnClickListener {
            email = userEmail.text.toString().trim()
            password = userPassword.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                userEmail.error = "Please enter your email"
                userEmail.requestFocus()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                userEmail.error = "Please enter your password"
                userEmail.requestFocus()
                return@setOnClickListener
            }

            Signin()
        }

        signupBtn.setOnClickListener {
            val intent = Intent(this@SignIn_activity, Signup_activity::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this@SignIn_activity, MainActivity::class.java))
            finish()
        }
    }


    private fun Signin() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email!!.trim(), password!!)
            .addOnSuccessListener { authResult ->
                val username = FirebaseAuth.getInstance().getCurrentUser()!!.getDisplayName()

                val intent = Intent(this@SignIn_activity, MainActivity::class.java)
                intent.putExtra("name",username)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                if (e is FirebaseAuthInvalidUserException) {
                    Toast.makeText(this@SignIn_activity, "User does not exist", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignIn_activity, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
