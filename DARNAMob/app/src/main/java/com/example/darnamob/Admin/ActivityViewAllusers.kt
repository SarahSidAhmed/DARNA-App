package com.example.darnamob.Admin



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
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
        my_recycler.adapter = ViewAllUsers(newList, db)
        my_recycler.layoutManager = LinearLayoutManager(this)
//        newRecyclerview.setHasFixedSize(true)
//
//        registerForContextMenu(newRecyclerview)
    }




}
