package com.example.darnamob.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.darnamob.R

class ReportSentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_sent)
        val button : Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, HomeAdmin::class.java)
            startActivity(intent)
        }
    }
}