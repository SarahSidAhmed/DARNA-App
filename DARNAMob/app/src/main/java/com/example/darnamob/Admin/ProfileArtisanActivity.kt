package com.example.darnamob.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import com.example.darnamob.R

class ProfileArtisanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_artisan2)
        val popUpBtn = findViewById<ImageView>(R.id.menu)
        popUpBtn.setOnClickListener { view->
            val popUpMenu = PopupMenu(this@ProfileArtisanActivity,view)
            popUpMenu.inflate(R.menu.admin_menu)
            popUpMenu.setOnMenuItemClickListener { menuItem->
                when(menuItem.itemId){
                    R.id.report ->{
                        //Toast.makeText(this@ProfileArtisanActivity,"Report", Toast.LENGTH_SHORT).show()
                        val window = PopupWindow(this)
                        val view  = layoutInflater.inflate(R.layout.card_confirmreport,null)
                        window.contentView = view
                        val button1 =view.findViewById<Button>(R.id.yes)
                        val button2 = view.findViewById<Button>(R.id.no)
                        button1.setOnClickListener {

                        }
                        button2.setOnClickListener {

                        }
                        true
                    }
                    R.id.Ban ->{
                        Toast.makeText(this@ProfileArtisanActivity,"Banned", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else ->{
                        false
                    }
                }
            }
            popUpMenu.show()
        }
    }
}