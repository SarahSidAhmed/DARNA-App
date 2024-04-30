package com.example.darnamob.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.R

class ActivityViewReportedusers : AppCompatActivity() , ReportedUsersAdapter.OnItemClickListener{

    private lateinit var newRecyclerview : RecyclerView
    private lateinit var db : DatabaseHelper
    private lateinit var newList : List<Artisan>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_allusers)
        newRecyclerview = findViewById(R.id.recyclerview)
        db = DatabaseHelper(this)
        newList = db.reportedUsers()
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)
        newRecyclerview.adapter = ReportedUsersAdapter(newList, db,this )
    }

    override fun onItemClick(artisan: Artisan) {
        // Start ProfileUser activity with the clicked user's data
        val email = artisan.membre.email
        val isClient = db.checkIfClient(email)
        if(isClient){
            val intent = Intent(this, ProfileArtisanActivity::class.java)
            intent.putExtra("email", artisan.membre.email)
            // Add more data if needed
            startActivity(intent)}
        else{val intent = Intent(this, ActivityProfileClient::class.java)
            intent.putExtra("email", artisan.membre.email)
            // Add more data if needed
            startActivity(intent)}
    }
}

