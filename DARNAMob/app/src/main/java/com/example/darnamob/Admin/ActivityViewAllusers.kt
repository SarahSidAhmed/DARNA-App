package com.example.darnamob.Admin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.R

class ActivityViewAllusers : AppCompatActivity() , ViewAllUsers.OnItemClickListener{

    private lateinit var newRecyclerview : RecyclerView
    private lateinit var db : DatabaseHelper
    private lateinit var newList : List<Artisan>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_allusers)
        newRecyclerview = findViewById(R.id.recyclerview)
        db = DatabaseHelper(this)
        newList = db.getAllUsers()
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)
        newRecyclerview.adapter = ViewAllUsers(newList, db,this )
        registerForContextMenu(newRecyclerview)
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
            intent.putExtra("phone", artisan.membre.tel)
            intent.putExtra("address", artisan.membre.address)
            // Add more data if needed
            startActivity(intent)}
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu!!.setHeaderTitle("Select Below")
        menu.add(0,v!!.id,0,"Report")
        menu.add(0,v!!.id,0,"Ban")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)
        if(item!!.title=="Ban"){
           // db.banishUser(artisan.member.id)
            val intent = Intent(this, BannedAccountActivity::class.java)
            startActivity(intent)
        }
        if(item!!.title=="Report"){
            val intent = Intent(this, ReportSentActivity::class.java)
        }
    }

}
