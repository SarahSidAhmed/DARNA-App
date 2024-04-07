package com.example.messaging

import com.example.messaging.SignIn_activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup_activity : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var signinBtn: TextView
    private lateinit var signupBtn: TextView
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        userName = findViewById(R.id.username)
        userEmail = findViewById(R.id.email)
        userPassword = findViewById(R.id.password)
        signinBtn = findViewById(R.id.login)
        signupBtn = findViewById(R.id.signup)

        signupBtn.setOnClickListener {
            username = userName.text.toString().trim()
            email = userEmail.text.toString().trim()
            password = userPassword.text.toString().trim()

            if (TextUtils.isEmpty(username)) {
                userName.error = "Please enter your username"
                userName.requestFocus()
                return@setOnClickListener
            }

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

          Signup()

        }

        signinBtn.setOnClickListener {
            val intent = Intent(this@Signup_activity, SignIn_activity::class.java)
            startActivity(intent)
        }
    }
    private fun Signup() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email!!.trim(), password!!)
            .addOnSuccessListener { authResult ->
                val userProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(username).build()

                val firebaseUser = FirebaseAuth.getInstance().currentUser
                firebaseUser?.updateProfile(userProfileChangeRequest)
                val userModel = UserModel(FirebaseAuth.getInstance().uid, username, email , password)
                databaseReference.child(FirebaseAuth.getInstance().uid!!).setValue(userModel)

                val intent = Intent(this@Signup_activity, MainActivity::class.java)
                intent.putExtra("name",username)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@Signup_activity,"Sign Up Failed", Toast.LENGTH_SHORT).show()
            }
    }


}
