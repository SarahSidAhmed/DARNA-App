package com.example.darnamob.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.R
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import com.example.darnamob.Admin.ViewAllUsers
import com.example.darnamob.Admin.CreateAccount
import com.example.darnamob.Admin.Fragments.CreateAccountFragment
import com.example.darnamob.Admin.Fragments.ViewAllusersFragment

class HomeAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val button: Button = findViewById(R.id.button)
        val button2: Button = findViewById(R.id.button2)
        button.setOnClickListener {
            val intent = Intent(this, ViewAllusersFragment::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, CreateAccountFragment::class.java)
           startActivity(intent)
        }
    }
}