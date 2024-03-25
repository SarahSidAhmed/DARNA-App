package com.example.darnamob.Client


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.darnamob.Client.Fragments.AccountFragment
import com.example.darnamob.Client.Fragments.DiscussionFragment
import com.example.darnamob.Client.Fragments.HomeFragment
import com.example.darnamob.Client.Fragments.SettingFragment
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityMainBinding
import com.example.darnamob.databinding.ActivityMainClientBinding


class MainActivityClient : AppCompatActivity() {

   private lateinit var binding : ActivityMainClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.background = null

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.message -> replaceFragment(DiscussionFragment())
                R.id.settings -> replaceFragment(SettingFragment())
                R.id.account -> replaceFragment(AccountFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}
