package com.example.darnamob.systems


import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R


class emailSystem {


    @SuppressLint("IntentReset")
    fun emailSend(email : String):Intent{
//        val email = db.getMemberEmailByID(id) //getting the email of the user from the database
        val subject =  "Important: Warning Regarding Your Recent Activity in DARNA Mob"
        val warningMessage = "Dear Mme/Mr,\n\n\n" +
                "\n" +
                "We\'re writing to bring to your attention some significant concerns raised by multiple reports about your recent activity within DARNA Mob.\n\n\n" +
                "\n" +
                "It\'s essential to address these issues promptly to maintain the integrity of our platform and ensure a positive experience for all users. Continued reports of this nature could result in further action, including potential account suspension.\\n\\n\n" +
                "\n" +
                "We\'re here to help and encourage you to reach out to our support team to discuss these matters further and find solutions together.\n\n\n" +
                "\n" +
                "Best regards,\n\n" +
                "Miss Sid Ahmed from DARNA Mob - Community Manager "

        val intent = Intent(Intent.ACTION_SEND)


        intent.data = Uri.parse("mailto: ")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, warningMessage)


        return intent

        //YOU NEED TO ADD THIS IN THE SEND WARNING ACTIVITY!!!!!!!!

//        try {
//            startActivity(Intent.createChooser(intent, "Send Email"))
//        }catch (e: Exception){
//            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
//        }

    }
}
