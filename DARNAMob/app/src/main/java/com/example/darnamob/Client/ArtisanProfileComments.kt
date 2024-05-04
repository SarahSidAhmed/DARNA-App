package com.example.darnamob.Client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityArtisanProfileCommentsBinding

private lateinit var db: DatabaseHelper
private lateinit var binding: ActivityArtisanProfileCommentsBinding
private var userId = -1
private var idArtisan = -1
class ArtisanProfileComments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtisanProfileCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("id", -1)
        idArtisan = intent.getIntExtra("idArtisan", -1)


        binding.back.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            intent.putExtra("id", userId)
        }

        val commentsList = db.getAllArtisanComments(idArtisan)
        val myRecyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        myRecyclerView.adapter = CommentAdapter(commentsList,this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myRecyclerView.layoutManager = layoutManager

       /* if (commentsList.size == 0){
            binding.noComments.visibility = View.VISIBLE
            binding.noCommentsText.visibility = View.VISIBLE
        }
        else{
            binding.noComments.visibility = View.GONE
            binding.noCommentsText.visibility = View.GONE
        }

        binding.commentBtn.setOnClickListener {

        }

        binding.rateBtn.setOnClickListener {

        }

        */
    }
}