package com.example.darnamob.systems


import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R


class emailSystem {


    @SuppressLint("IntentReset")
    fun emailSend(id :Int, db: DatabaseHelper){
        val email = db.getMemberEmailByID(id) //getting the email of the user from the database
        val subject =  Resources.getSystem().getString(R.string.subject)
        val warningMessage = Resources.getSystem().getString(R.string.Warning_Message)

        val intent = Intent(Intent.ACTION_SEND)


        intent.data = Uri.parse("mailto: ")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, warningMessage)


        //YOU NEED TO ADD THIS IN THE SEND WARNING ACTIVITY!!!!!!!!

//        try {
//            startActivity(Intent.createChooser(intent, "Send Email"))
//        }catch (e: Exception){
//            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
//        }

    }
}
