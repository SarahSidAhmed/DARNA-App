package com.example.darnamob.Client

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.darnamob.Artisant.Fragments.EditSettingFragment
import com.example.darnamob.Artisant.Fragments.SettingFragment
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityEditPasswordSettingsBinding

private lateinit var binding: ActivityEditPasswordSettingsBinding
private lateinit var db: DatabaseHelper
private  var userId = -1

class EditPasswordSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPasswordSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("id", -1)

        db = DatabaseHelper(this)
        userId = 1
        logic()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun logic() {

        binding.back.setOnClickListener {
            val intent = Intent(this, SettingFragment::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }
        binding.save.setOnClickListener {
            val oldPassword = binding.oldPassword.text.toString()
            val newPassword = binding.newPassword.text.toString()
            val confirm = binding.confirm.text.toString()

            if (newPassword == confirm){
            val bool = db.editPassword(userId, oldPassword, newPassword)

                if (bool){
                    Toast.makeText(this, "Password Changed", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

            }
            else Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
        }
    }
}