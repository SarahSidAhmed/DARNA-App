package com.example.darnamob.Client


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.darnamob.Client.Fragments.AccountFragment
import com.example.darnamob.Client.Fragments.DiscussionFragment
import com.example.darnamob.Client.Fragments.HomeFragment
import com.example.darnamob.Client.Fragments.SettingFragment
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityMainClientBinding


class MainActivityClient : AppCompatActivity() {

   private lateinit var binding : ActivityMainClientBinding
   private lateinit var db : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        replaceFragment(HomeFragment())
        binding.bottomNavigationView.background = null

        val userId = 2 //intent.getIntExtra("id", -1) // to get the id



        //new order button

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.home -> { //done


                    val bundle1 = Bundle().apply {
                        putInt("id", userId)
                    }
                    val homeFrag = HomeFragment().apply {
                        arguments = bundle1
                    }
                    replaceFragment(homeFrag)
                }

                R.id.message -> {
                    val bundle = Bundle().apply {
                        putInt("id", userId)
                    }
                    val messageFrag = DiscussionFragment().apply {
                        arguments = bundle

                    }
                    replaceFragment(DiscussionFragment())}

                R.id.settings -> { //started
                    val bundle = Bundle().apply {
                        putInt("id", userId)
                    }
                    bundle.putInt("id", userId)
                    val settingsFrag = SettingFragment().apply {
                        arguments = bundle
                    }
                    replaceFragment(SettingFragment())}

                R.id.account -> {
                    val bundle = Bundle()
                    bundle.putInt("id", userId)
                    val accountFrag = AccountFragment()
                    accountFrag.arguments = bundle
                    replaceFragment(AccountFragment())}
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
