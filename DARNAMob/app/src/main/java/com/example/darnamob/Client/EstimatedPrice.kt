package com.example.darnamob.Client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.databinding.ActivityEstimatedPriceBinding

private lateinit var db: DatabaseHelper
private lateinit var binding: ActivityEstimatedPriceBinding

class EstimatedPrice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstimatedPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = DatabaseHelper(this)

        //make the add new order send the price, calculate it directly in the add new order
        val userId = intent.getIntExtra("id", -1) // to get the id



    }
}