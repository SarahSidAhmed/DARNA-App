package com.example.darnamob.Client.Fragments

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import com.example.darnamob.Client.ArtisanProfileComments
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R

private lateinit var db: DatabaseHelper
private var userId = -1;
class RateArtisan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_artisan)


        db = DatabaseHelper(this)
        userId = intent.getIntExtra("id", -1)
        val idArtisan = intent.getIntExtra("artisanId", -1)


        findViewById<ImageView>(R.id.back).setOnClickListener {
            val intent = Intent(this, ArtisanProfileComments::class.java)

            intent.putExtra("id", userId)
            intent.putExtra("idArtisan", idArtisan)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.rateButton).setOnClickListener {

            val num = findViewById<RatingBar>(R.id.ratingBar)
            val number = num.rating

            Toast.makeText(this, number.toString(), Toast.LENGTH_SHORT).show()
            val stars = findViewById<RatingBar>(R.id.ratingBar).numStars
            val comment = findViewById<EditText>(R.id.commentText).text.toString().trim()

            if (comment.isNotEmpty()) {
                db.addComment(idArtisan, userId, comment, number)
                val intent = Intent(this, ArtisanProfileComments::class.java)

                intent.putExtra("id", userId)
                intent.putExtra("idArtisan", idArtisan)
                startActivity(intent)
                finish()
            } else Toast.makeText(this, "Comment can not be empty!", Toast.LENGTH_SHORT).show()
        }
    }
}