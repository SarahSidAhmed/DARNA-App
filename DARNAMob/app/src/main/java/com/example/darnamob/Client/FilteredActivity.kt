package com.example.darnamob.Client

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import kotlinx.coroutines.MainScope


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


        findViewById<ImageView>(R.id.imageView2).setOnClickListener {
            val intent = Intent(this, MainActivityClient::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }
        var listTasks = db.filterRendezVousByCategorie(userId, filter)


        val myRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        myRecyclerView.adapter = FilteredAdapter(listTasks,this)
        val layoutManager = LinearLayoutManager(this)
        myRecyclerView.layoutManager = layoutManager

        val num = listTasks.size

        if (num == 0){
            findViewById<ImageView>(R.id.noImage).visibility = View.VISIBLE
            findViewById<TextView>(R.id.noText).visibility = View.VISIBLE
        }
        else
        {
            findViewById<ImageView>(R.id.noImage).visibility = View.GONE
            findViewById<TextView>(R.id.noText).visibility = View.GONE
        }



    }


}