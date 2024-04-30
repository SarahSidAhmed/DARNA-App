package com.example.darnamob.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.R

class ActivityViewAllusers : AppCompatActivity() {

    private lateinit var db : DatabaseHelper
    private lateinit var newList : List<Artisan>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_allusers)

        db = DatabaseHelper(this)

        newList = db.getAllUsers()

        val my_recycler = findViewById<RecyclerView>(R.id.my_recyclerview)

        my_recycler.adapter = ViewAllUsers(newList, this)
        my_recycler.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.backArrow).setOnClickListener {
            val intent = Intent(this, HomeAdmin::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.btnReported).setOnClickListener {
            val intent = Intent(this, ActivityViewReportedusers::class.java)
            startActivity(intent)
            finish()
        }
//        newRecyclerview.setHasFixedSize(true)
//
//        registerForContextMenu(newRecyclerview)
    }




}
