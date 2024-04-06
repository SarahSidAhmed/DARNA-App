package com.example.darnamob.Artisant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import com.example.darnamob.Client.MainActivityClient
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityNotificationsBinding

private lateinit var db : DatabaseHelper
private lateinit var binding: ActivityNotificationsBinding

class Notifications : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        val userId = intent.getIntExtra("id", -1)
        val notifications = db.notificationByID(userId) //this is the list of notifications use it in the adapter

        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivityClient::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }
    }
}
