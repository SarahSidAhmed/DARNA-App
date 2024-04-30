package com.example.darnamob.Client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityArtisanProfileCommentsBinding

private lateinit var db: DatabaseHelper
private lateinit var binding: ActivityArtisanProfileCommentsBinding
private var userId = -1

class ArtisanProfileComments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtisanProfileCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("id", -1)

        binding.back.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            intent.putExtra("id", userId)
        }

    }
}