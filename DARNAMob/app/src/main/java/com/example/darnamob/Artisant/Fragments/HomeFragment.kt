package com.example.darnamob.Artisant.Fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Artisant.Home_adapter
import com.example.darnamob.Artisant.Notifications
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R


class HomeFragment : Fragment() {

    private var userId: Int =-1
    private lateinit var db : DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home_fragment, container, false)
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

        view?.findViewById<ImageView>(R.id.notif)?.setOnClickListener {
            val intent = Intent(requireActivity(), Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }
        val text = view?.findViewById<TextView>(R.id.hello_art)?.text
        view?.findViewById<TextView>(R.id.hello_art)?.setText("Welcome\n"+artisan.membre.userName)

        val image = artisan.membre.image

        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        view?.findViewById<ImageView>(R.id.artProfilePic)?.setImageBitmap(bitmap)

        //this is the list of demandes put it in the adapter
        val demandes = db.getAllDemandeByRegionDispo(artisan.work_Area, artisan.disponible)
        if (demandes.size == 0) {
            view?.findViewById<ImageView>(R.id.noDemande)?.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.textNoDemande)?.visibility = View.VISIBLE
        }
        else {
            view?.findViewById<ImageView>(R.id.noDemande)?.visibility = View.GONE
            view?.findViewById<TextView>(R.id.textNoDemande)?.visibility = View.GONE

        }


        val recyclerView= view?.findViewById<RecyclerView>(R.id.recycler)

        recyclerView?.adapter = Home_adapter(demandes, requireContext(), userId)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())


        recyclerView?.layoutManager = LinearLayoutManager(context)



        view?.findViewById<EditText>(R.id.searchBar)?.setOnKeyListener { v, keyCode, event ->

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                Toast.makeText(requireContext(), "SET!!", Toast.LENGTH_SHORT).show()
                return@setOnKeyListener true
            }
            false

        }



    }


}