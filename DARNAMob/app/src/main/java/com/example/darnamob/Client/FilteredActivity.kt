package com.example.darnamob.Client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R


private lateinit var db: DatabaseHelper
private var userId=-1
private var filter = ""
class FilteredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered)


        userId = intent.getIntExtra("id", -1)
        filter = intent.getStringExtra("filter").toString()
        db = DatabaseHelper(this)


        val listTasks = db.filterRendezVousByCategorie(userId, filter)
        val myRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myRecyclerView.layoutManager = layoutManager


    }
}