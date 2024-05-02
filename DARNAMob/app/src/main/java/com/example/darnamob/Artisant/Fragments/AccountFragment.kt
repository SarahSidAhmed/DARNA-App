package com.example.darnamob.Artisant.Fragments


import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import android.widget.Switch
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import com.example.darnamob.Artisant.Notifications
import com.example.darnamob.R
import com.example.darnamob.Database.DatabaseHelper

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
        view?.findViewById<ImageView>(R.id.art_profil_pic)?.setImageBitmap(bitmap)

        //setting the other infos
        view?.findViewById<TextView>(R.id.artisant_name)?.setText(artisan.membre.userName)
        view?.findViewById<TextView>(R.id.art_mail1)?.setText(artisan.membre.email)
        view?.findViewById<TextView>(R.id.art_address)?.setText(artisan.membre.address)
        view?.findViewById<TextView>(R.id.art_phone1)?.setText(artisan.membre.tel)
        view?.findViewById<TextView>(R.id.art_workhours1)?.setText(db.getWorkHour(userId))
        view?.findViewById<TextView>(R.id.multiAutoCompleteTextView1)?.setText(artisan.work_Area)

        view?.findViewById<ImageView>(R.id.notif)?.setOnClickListener {
            val intent = Intent(requireContext(), Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }
        //you need to do the workdays
        val blueColor = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.workdaysColor ))

        val days = db.getWorkDays(userId)  //this is the array do the necessary logic
        val toggles = arrayOf(
            view?.findViewById<ToggleButton>(R.id.sat1),
            view?.findViewById<ToggleButton>(R.id.sun1),
            view?.findViewById<ToggleButton>(R.id.mon1),
            view?.findViewById<ToggleButton>(R.id.Tue1),
            view?.findViewById<ToggleButton>(R.id.wed1),
            view?.findViewById<ToggleButton>(R.id.thu1),
            view?.findViewById<ToggleButton>(R.id.fri1)
        )

        val colorStateList = ContextCompat.getColorStateList(requireContext(), R.color.my_custom_blue)
        //setting the state of the toggle buttons
        var i =0
        for (item in toggles){
            item?.isEnabled = false
            if (days[i]) item?.backgroundTintList = colorStateList
            i++
        }

        view?.findViewById<Switch>(R.id.status1)?.isChecked = artisan.disponible
        view?.findViewById<Switch>(R.id.deplacement1)?.isChecked = artisan.deplacement



    }

}


