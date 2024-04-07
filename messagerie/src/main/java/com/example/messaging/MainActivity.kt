package com.example.messaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerview : RecyclerView
    private lateinit var useradapter : UserAdapter
    val databaseRefrence : DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var yourname : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val userName = intent.getStringExtra("username")
        supportActionBar?.title = userName
        val userAdapter = UserAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.adapter = UserAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val databaseReference = FirebaseDatabase.getInstance().getReference("users")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userAdapter.clear()
                for (dataSnapshot in snapshot.children) {
                    val uid = dataSnapshot.key
                    val userModel = dataSnapshot.getValue(UserModel::class.java)
                    if (userModel != null && userModel.userId != null && userModel.userId != FirebaseAuth.getInstance().uid) {
                        userAdapter.add(userModel)
                    }
                }
                val userModelList = userAdapter.userModelList
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })


    }

    private fun setSupportActionBar(toolbar: Toolbar) {

    }
}