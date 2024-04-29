package com.example.darnamob.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.R

class ActivityViewReportedusers : AppCompatActivity() {

    private lateinit var newRecyclerview : RecyclerView
    private lateinit var db : DatabaseHelper
    private lateinit var newList : List<Artisan>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_allusers)
        newRecyclerview = findViewById(R.id.my_recyclerview)
        newRecyclerview.adapter = ReportedUsersAdapter(newList, db)
        db = DatabaseHelper(this)
        newList = db.reportedUsers()
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        //newRecyclerview.setHasFixedSize(true)

    }
}

