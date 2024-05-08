package com.example.darnamob.Client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Notification
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityNotificationsBinding

private lateinit var binding : ActivityNotificationsBinding

class Notifications : AppCompatActivity() {

    private lateinit var db : DatabaseHelper
    private lateinit var notifs : List<Notification>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        val userId = intent.getIntExtra("id", -1)

         notifs = db.notificationByID(userId) // this is the list of notifications use it in the adapter

        val artisanProfile = findViewById<CardView>(R.id.notif_item_artisan)

        val my_recycler = findViewById<RecyclerView>(R.id.notif_recycler)
        my_recycler.adapter=NotificationAdapter(notifs,this)
        my_recycler.layoutManager =LinearLayoutManager(this)

        if(notifs.size == 0 ){
            findViewById<ImageView>(R.id.noNotifications).visibility = View.VISIBLE
            findViewById<TextView>(R.id.noNotifText).visibility = View.VISIBLE
        }
        else{
            findViewById<ImageView>(R.id.noNotifications).visibility = View.GONE
            findViewById<TextView>(R.id.noNotifText).visibility = View.GONE
        }

        binding.backNotif.setOnClickListener {
            val intent = Intent(this, MainActivityClient::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }
    }
}