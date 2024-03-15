package com.example.darnamob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import com.example.darnamob.Database.Admin
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.Table_Schemas

private lateinit var db : DatabaseHelper
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        db = DatabaseHelper(this)
        val admin = Admin(0, "ms_kadid@esi.dz", "1234".toSHA256())
        db.insertAdmin(admin)

    }
}