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

class ActivityViewReportedusers : AppCompatActivity() {

    private lateinit var newRecyclerview : RecyclerView
    private lateinit var db : DatabaseHelper
    private lateinit var newList : List<Artisan>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewreportedusers)

        db = DatabaseHelper(this)
        newList = db.reportedUsers()

        val my_recyclerView = findViewById<RecyclerView>(R.id.reportedRecyclerView)
        my_recyclerView.adapter = ViewAllUsers(newList, this)
        my_recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.backArrow).setOnClickListener {
            val intent = Intent(this, HomeAdmin::class.java)
            startActivity(intent)
            finish()
        }

        //bug here, it seems like he can't find the btnA, I dunno why
        findViewById<LinearLayout>(R.id.btnA).setOnClickListener {
            val intent = Intent(this, ActivityViewAllusers::class.java)
            startActivity(intent)
            finish()
        }

//        findViewById<LinearLayout>(R.id.btnAll).setOnClickListener {
//            val intent = Intent(this, ActivityViewAllusers::class.java)
//            startActivity(intent)
//            finish()
//        }



//        newRecyclerview = findViewById(R.id.my_recyclerview)
//        newRecyclerview.adapter = ReportedUsersAdapter(newList, db)
//        db = DatabaseHelper(this)
//        newList = db.reportedUsers()
//        newRecyclerview.layoutManager = LinearLayoutManager(this)
//        //newRecyclerview.setHasFixedSize(true)

    }


}

