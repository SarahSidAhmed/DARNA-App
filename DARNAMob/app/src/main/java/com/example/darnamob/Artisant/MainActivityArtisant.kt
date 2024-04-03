package com.example.darnamob.Artisant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.R
import androidx.fragment.app.Fragment
import com.example.darnamob.Client.Fragments.AccountFragment
import com.example.darnamob.Client.Fragments.DiscussionFragment
import com.example.darnamob.Client.Fragments.HomeFragment
import com.example.darnamob.Client.Fragments.SettingFragment
import com.example.darnamob.databinding.ActivityMainArtisantBinding
import com.example.darnamob.databinding.ActivityMainBinding
import com.example.darnamob.databinding.ActivityMainClientBinding

class MainActivityArtisant : AppCompatActivity() {

    private lateinit var binding : ActivityMainArtisantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainArtisantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(com.example.darnamob.Artisant.Fragments.HomeFragment())

        binding.bottomNavigationView.background = null

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(com.example.darnamob.Artisant.Fragments.HomeFragment())
                R.id.message -> replaceFragment(DiscussionFragment())
                R.id.settings -> replaceFragment(SettingFragment())
                R.id.account -> replaceFragment(AccountFragment())
            }
            true
        }

        val userId = intent.getIntExtra("id", -1)
        //tr
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}


