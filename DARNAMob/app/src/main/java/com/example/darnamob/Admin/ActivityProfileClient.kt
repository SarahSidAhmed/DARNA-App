package com.example.darnamob.Admin

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.databinding.ActivityProfileClientBinding
import com.example.darnamob.systems.emailSystem

private lateinit var binding : ActivityProfileClientBinding
class ActivityProfileClient : AppCompatActivity() {
    private lateinit var db : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        val id = intent.getIntExtra("id",-1)
        val member = db.getMembreByID(id)
        val email = member.email
        val phone = member.tel
        val address = member.address
        val name = member.userName
        val image = member.image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        //val bundle = Bundle()
        //bundle.putInt("Int",id)

        binding.artisantName.setText(name)
        binding.email.setText(email)
        binding.address.setText(address)
        binding.phone.setText(phone)
        binding.artProfilPic.setImageBitmap(bitmap)

        val popUpBtn = findViewById<ImageView>(R.id.menu)
        popUpBtn.setOnClickListener { view->
            val popUpMenu = PopupMenu(this@ActivityProfileClient,view)
            popUpMenu.inflate(R.menu.admin_menu)
            popUpMenu.setOnMenuItemClickListener { menuItem->
                when(menuItem.itemId){
                    R.id.report ->{
                        //db.insertADminWarning(id)
                        emailSystem().emailSend(email)
                        try {
                                 startActivity(Intent.createChooser(intent, "Send Email"))
                        }
                        catch (e: Exception){
                                 Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        }
                        var dialog = SucReportDialogFragmnet()
                        //dialog.arguments = bundle
                        dialog.show(supportFragmentManager,"customDialog")

                        true
                    }
                    R.id.Ban ->{
                        db.banishUser(id)
                        var dialog = SucDeleteDialogFragment()
                        //dialog.arguments = bundle
                        dialog.show(supportFragmentManager,"customDialog")
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