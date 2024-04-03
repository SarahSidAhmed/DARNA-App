package com.example.darnamob.Client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityMainClientBinding
import com.example.darnamob.databinding.ActivityNotificationsBinding


private lateinit var binding : ActivityNotificationsBinding

class Notifications : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("id", -1)

        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivityClient::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }
    }
}