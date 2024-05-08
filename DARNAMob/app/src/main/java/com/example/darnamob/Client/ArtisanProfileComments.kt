package com.example.darnamob.Client

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        db = DatabaseHelper(this)

        userId = intent.getIntExtra("id", -1)
        idArtisan = intent.getIntExtra("idArtisan", -1)


        binding.back.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            intent.putExtra("id", userId)
        }

        val commentsList = db.getAllArtisanComments(idArtisan)
        val myRecyclerView = findViewById<RecyclerView>(R.id.my_recycler_viewComment)
        myRecyclerView.adapter = CommentAdapter(commentsList,this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myRecyclerView.layoutManager = layoutManager

        val artisan = db.getArtisanByID(idArtisan)

        binding.userName.setText(artisan.membre.userName)
        binding.rateNum.setText(artisan.Rating.toString())
        val image = artisan.membre.image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        binding.ProfileImage.setImageBitmap(bitmap)

        binding.region.text = artisan.work_Area
        binding.domain.text = artisan.domain

       if (commentsList.size == 0){
            binding.noImage.visibility = View.VISIBLE
            binding.noText.visibility = View.VISIBLE
        }
        else{
            binding.noImage.visibility = View.GONE
            binding.noText.visibility = View.GONE
        }

        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivityClient::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }

        binding.report.setOnClickListener {
            db.reportUser(idArtisan)
            Toast.makeText(this, "User reported!", Toast.LENGTH_SHORT).show()
        }
        binding.addComment.setOnClickListener {


        }

        binding.rateBtn.setOnClickListener {

        }
    }
}