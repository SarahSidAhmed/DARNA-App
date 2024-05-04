package com.example.darnamob.Artisant


import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import com.example.darnamob.Artisant.Fragments.Calendar
import com.example.darnamob.Client.Notifications
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.Database.data.RendezVousTasks
import com.example.darnamob.R
import com.example.darnamob.databinding.ArtNotifBinding
import com.example.darnamob.databinding.CalendarBinding

private lateinit var binding: CalendarBinding

class Calendrier : AppCompatActivity() {

    private var userId: Int = -1
    private lateinit var db : DatabaseHelper
    private lateinit var tasks : List<RendezVousTasks>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the user ID from the intent extras
        userId = intent.getIntExtra("id", -1)

        // Initialize the database helper
        db = DatabaseHelper(this)
        val my_recycler = findViewById<RecyclerView>(R.id.my_recycler_view)
        val tasks = db.getTasksArtisan(userId)
        val calendar = findViewById<CalendarView>(R.id.calendar)


        my_recycler.adapter = CalendrierAdapter(tasks, this)
        my_recycler.layoutManager = LinearLayoutManager(this)


        findViewById<ImageView>(R.id.notif)?.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }


        // Perform your logic here
        logic(userId)
    }


    private fun logic(userId: Int) {

        val membre = db.getMembreByID(userId)


        // Setting the onclick of the notification
        findViewById<ImageView>(R.id.notif)?.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }

        // Get all the tasks of the artisant
        val rendezvous = db.getTasksArtisan(userId)




    }
}