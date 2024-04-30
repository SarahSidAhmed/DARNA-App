package com.example.darnamob.Artisant.Fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.Artisant.MainActivityArtisant
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.databinding.ActivityNotificationsBinding

private lateinit var db: DatabaseHelper
private lateinit var binding : ActivityNotificationsBinding
class Notifications : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        val userId = intent.getIntExtra("id", -1) // to get the id

       val notification = db.notificationByID(userId) //this is the list of notifications, use it in the adapters

        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivityArtisant::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }


    }
}
