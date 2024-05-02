package com.example.darnamob.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.darnamob.Accueil.Accueil3
import com.example.darnamob.Admin.HomeAdmin
import com.example.darnamob.Artisant.MainActivityArtisant
import com.example.darnamob.Client.MainActivityClient
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.databinding.ActivitySignInBinding

private lateinit var binding : ActivitySignInBinding
private lateinit var db: DatabaseHelper
class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.SubmitButtom.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
            Signin(email, password)}
            else Toast.makeText(this, "Fields can not be empty, check your infos again", Toast.LENGTH_SHORT).show()
        }

        binding.signupText.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }

        binding.back.setOnClickListener {
            startActivity(Intent(this, Accueil3::class.java))
        }



    }

    private fun Signin(email: String, password: String) {
        db = DatabaseHelper(this)
        val userExist = db.checkEmail(email)

        //the user must exist in the login
        if (userExist){
            val checkCredential = db.checkEmailPassword(email, password)

            if (checkCredential){
                if(db.checkIfAdmin(email)){ //if the user is an admin go to the main admin
                    startActivity(Intent(this, HomeAdmin::class.java)) //have to change it to main admin activity
                    finish()
                }else{
                    Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()

                    val id = db.getUserID(email)

                    if (db.checkIfClient(email)){ // if the user is a client go to the main client and store the current user type member

                        val intent = Intent(this, MainActivityClient::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                        finish()

                    }else{ //if the user is an artisan go to the main artisan and store the current user type artisan
                        Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivityArtisant::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            else{
                Toast.makeText(this, "Something went wrong. Check you password.", Toast.LENGTH_SHORT).show()
            }


        }else{
            Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }
}