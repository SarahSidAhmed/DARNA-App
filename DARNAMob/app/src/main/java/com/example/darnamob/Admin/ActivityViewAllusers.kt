package com.example.darnamob.Admin



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.R

class ActivityViewAllusers : AppCompatActivity() {

    private lateinit var db : DatabaseHelper
    private lateinit var newList : List<Artisan>
    private lateinit var searchList : List<Artisan>
    private lateinit var searchview : androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_allusers)

        db = DatabaseHelper(this)
        newList = db.getAllUsers()
        val my_recycler = findViewById<RecyclerView>(R.id.my_recyclerview)
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
                    Toast.makeText(this@ActivityViewAllusers,  "No data found", Toast.LENGTH_SHORT).show()
                } else {
                    my_recycler.adapter = ReportedUsersAdapter(searchList, this@ActivityViewAllusers)
                }

                return true
            }
        })


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
