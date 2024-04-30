package com.example.darnamob.Artisant.Fragments


import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.darnamob.R
import com.example.darnamob.Database.DatabaseHelper
import org.w3c.dom.Text

class AccountFragment : Fragment() {

    private var userId: Int =-1
    private lateinit var db : DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let { bundle ->
            userId = bundle.getInt("id", -1)
        }

        // Initialize the database helper
        db = DatabaseHelper(requireContext())

        // Perform your logic here
        logic(userId)
    }

    private fun logic(userId: Int) {

        val artisan = db.getArtisanByID(userId)


        //setting the profile pic
        val profile = artisan.membre.image
        val bitmap = BitmapFactory.decodeByteArray(profile, 0, profile.size)
        view?.findViewById<ImageView>(R.id.artprofilpic)?.setImageBitmap(bitmap)

        //setting the other infos
        view?.findViewById<TextView>(R.id.art_mail1)?.setText(artisan.membre.email)
        view?.findViewById<TextView>(R.id.art_address)?.setText(artisan.membre.address)
        view?.findViewById<TextView>(R.id.art_phone1)?.setText(artisan.membre.tel)
        view?.findViewById<TextView>(R.id.art_workhours1)?.setText(db.getWorkHour(userId))

        //you need to do the workdays
        val days = db.getWorkDays(userId)  //this is the array do the necessary logic







    }

}


