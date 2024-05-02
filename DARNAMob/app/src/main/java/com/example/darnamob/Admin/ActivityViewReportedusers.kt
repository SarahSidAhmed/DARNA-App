package com.example.darnamob.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.R

class ActivityViewReportedusers : AppCompatActivity() {

    private lateinit var newRecyclerview : RecyclerView
    private lateinit var db : DatabaseHelper
    private lateinit var newList : List<Artisan>
    private lateinit var searchList : List<Artisan>
    private lateinit var searchview : androidx.appcompat.widget.SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewreportedusers)

        val my_recyclerView = findViewById<RecyclerView>(R.id.reportedRecyclerView)

        db = DatabaseHelper(this)
        newList = db.reportedUsers()
        searchview = findViewById<androidx.appcompat.widget.SearchView>(R.id.search_bar)
        searchview.clearFocus()
        searchview.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // handle query submit
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                searchList = db.searchUserByName(newText)
                if (searchList.isEmpty()) {
                    Toast.makeText(this@ActivityViewReportedusers,  "No data found", Toast.LENGTH_SHORT).show()
                } else {
                    my_recyclerView.adapter = ReportedUsersAdapter(searchList, this@ActivityViewReportedusers)
                }

                return true
            }
        })

        my_recyclerView.adapter = ReportedUsersAdapter(newList, this)
        my_recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.backArrow).setOnClickListener {
            val intent = Intent(this, HomeAdmin::class.java)
            startActivity(intent)
            finish()
        }

        //bug here, it seems like he can't find the btnA, I dunno why
        findViewById<LinearLayout>(R.id.btnAll).setOnClickListener {
            val intent = Intent(this, ActivityViewAllusers::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.btnReported).setOnClickListener {
            val intent = Intent(this, ActivityViewAllusers::class.java)
            startActivity(intent)
            finish()
        }



//        newRecyclerview = findViewById(R.id.my_recyclerview)
//        newRecyclerview.adapter = ReportedUsersAdapter(newList, db)
//        db = DatabaseHelper(this)
//        newList = db.reportedUsers()
//        newRecyclerview.layoutManager = LinearLayoutManager(this)
//        //newRecyclerview.setHasFixedSize(true)

    }


}

