package com.example.darnamob


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Prestation
import com.example.darnamob.databinding.ActivityMainBinding


private lateinit var db : DatabaseHelper
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = DatabaseHelper(this)
        val email = "ms_kadid@.dz"
        val pass = "1234"
        var check = db.checkEmailPassword(email, pass)
        if(check) Toast.makeText(this@MainActivity, "YES", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this@MainActivity, "NO", Toast.LENGTH_SHORT).show()


    }
}