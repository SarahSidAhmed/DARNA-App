package com.example.darnamob.Artisant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Notification
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityNotificationsBinding
import com.example.darnamob.databinding.ArtNotifBinding

private lateinit var db : DatabaseHelper
private lateinit var binding: ArtNotifBinding

class Notifications : AppCompatActivity() {

    private lateinit var db : DatabaseHelper
    private lateinit var notifications : List<Notification>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ArtNotifBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        val userId = intent.getIntExtra("id", -1)
         notifications = db.notificationByID(userId) //this is the list of notifications use it in the adapter



        val my_recycler = findViewById<RecyclerView>(R.id.notif_recycler)
        my_recycler.adapter = Notif_adapter(notifications, this)
        my_recycler.layoutManager = LinearLayoutManager(this)




        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivityArtisant::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }
    }
}
