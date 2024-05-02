package com.example.darnamob.Artisant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.R
import androidx.fragment.app.Fragment
import com.example.darnamob.Artisant.Fragments.Calendar
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.databinding.ActivityMainArtisantBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

private lateinit var binding : ActivityMainArtisantBinding
private lateinit var db : DatabaseHelper
private lateinit var bundle: Bundle

class MainActivityArtisant : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainArtisantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        var userId = intent.getIntExtra("id", -1) // to get the id
        bundle = Bundle().apply {
            putInt("id", userId)
        }

        val homeFrag = com.example.darnamob.Artisant.Fragments.HomeFragment().apply {
            arguments = bundle
        }
        replaceFragment(homeFrag)

        binding.bottomNavigationView.background = null

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    bundle = Bundle().apply {
                        putInt("id", userId)
                    }

                    val homeFrag = com.example.darnamob.Artisant.Fragments.HomeFragment().apply {
                        arguments = bundle
                    }
                    replaceFragment(homeFrag)
                }
                R.id.message -> {
                    bundle = Bundle().apply {
                        putInt("id", userId)
                    }

                    val messageFrag = com.example.darnamob.Artisant.Fragments.DiscussionFragment().apply {
                        arguments = bundle
                    }
                    replaceFragment(messageFrag)
                }
                R.id.settings -> {
                    bundle = Bundle().apply {
                        putInt("id", userId)
                    }
                    val settingsFrag = com.example.darnamob.Artisant.Fragments.SettingFragment().apply {
                        arguments = bundle
                    }
                    replaceFragment(settingsFrag)
                }
                R.id.account -> {
                    bundle = Bundle().apply {
                        putInt("id", userId)
                    }
                    val accountFrag = com.example.darnamob.Artisant.Fragments.AccountFragment().apply {
                        arguments = bundle
                    }
                    replaceFragment(accountFrag)
                }

                R.id.calendatButton ->{
                    bundle = Bundle().apply {
                        putInt("id", userId)
                    }

                    val calendarFrag = Calendar().apply {
                        arguments = bundle
                    }
                    replaceFragment(calendarFrag)
                }
            }
            true
        }

//        findViewById<FloatingActionButton>(R.id.calendar).setOnClickListener {
//            val intent = Intent(this, Calendar::class.java)
//            intent.putExtra("id", userId)
//            startActivity(intent)
//            finish()
//        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}


