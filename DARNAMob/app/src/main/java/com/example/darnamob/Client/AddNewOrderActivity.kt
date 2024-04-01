package com.example.darnamob.Client


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.darnamob.R

class AddNewOrderActivity : AppCompatActivity() {
    private lateinit var autoCompleteTxt: AutoCompleteTextView

    // Items array
    private val items = arrayOf("Painting", "Plumbing", "Cleaning", "Masonry", "Electricity")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_order)

        // Initialize the AutoCompleteTextView
        //autoCompleteTxt = findViewById(R.id.auto_complete_txt)

        // Create adapter for AutoCompleteTextView
       // val adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, items)

        // Set adapter to AutoCompleteTextView
       // autoCompleteTxt.setAdapter(adapter)
    }
}