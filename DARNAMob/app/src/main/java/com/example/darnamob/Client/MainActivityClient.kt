package com.example.darnamob.Client


import android.content.Intent
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
   private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        val userId = 1 //intent.getIntExtra("id", -1) // to get the id

         bundle = Bundle().apply {
            putInt("id", userId)
        }
        val homeFrag = HomeFragment().apply {
            arguments = bundle
        }
        replaceFragment(homeFrag)

        binding.bottomNavigationView.background = null


        //new order button
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddNewOrderActivity::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
                item ->
            when (item.itemId) {
                R.id.home -> { //done
                    val homeFrag = HomeFragment().apply {
                        arguments = bundle
                    }
                    replaceFragment(homeFrag)
                }

                R.id.message -> {
                    val messageFrag = DiscussionFragment().apply {
                        arguments = bundle

                    }
                    replaceFragment(messageFrag)}

                R.id.settings -> { //started
                    val settingsFrag = SettingFragment().apply {
                        arguments = bundle
                    }
                    replaceFragment(settingsFrag)}

                R.id.account -> {
                    val accountFrag = AccountFragment()
                    accountFrag.arguments = bundle
                    replaceFragment(accountFrag)}
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
