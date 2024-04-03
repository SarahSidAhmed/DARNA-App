package com.example.darnamob.Client.Fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Client.Notifications
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.MainActivity
import com.example.darnamob.R


class HomeFragment : Fragment() {

    private var userId: Int =-1
    private lateinit var db : DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.my_recycler_view)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        // Inflate the layout for this fragment
        return rootView

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
        val membre = db.getMembreByID(userId)

        // Setting the username welcome
        view?.findViewById<TextView>(R.id.username)?.text = "${membre.userName}!"
        var image = membre.image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        view?.findViewById<ImageView>(R.id.profile_photo)?.setImageBitmap(bitmap)


        // Setting the onclick of the notification
        view?.findViewById<ImageView>(R.id.notif)?.setOnClickListener {
            val intent = Intent(requireActivity(), Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }

        // Get all the rendez-vous of the client
        val rendezvous = db.getRendezVousClient(userId)

        // Setting the filtration buttons
        val painting = view?.findViewById<ImageView>(R.id.painting)
        val plumbing = view?.findViewById<ImageView>(R.id.plumbing)
        val electricity = view?.findViewById<ImageView>(R.id.electricity)
        val cleaning = view?.findViewById<ImageView>(R.id.cleaning)
        val masonry = view?.findViewById<ImageView>(R.id.masonry)

        painting?.setOnClickListener {
            val filter = "Painter"
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("filter", filter)
            startActivity(intent)
        }

        plumbing?.setOnClickListener {
            val filter = "Plumber"
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("filter", filter)
            startActivity(intent)
        }

        electricity?.setOnClickListener {
            val filter = "Electrician"
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("filter", filter)
            startActivity(intent)
        }

        masonry?.setOnClickListener {
            val filter = "Maconier"
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("filter", filter)
            startActivity(intent)
        }

        cleaning?.setOnClickListener {
            val filter = "Cleaning"
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("filter", filter)
            startActivity(intent)
        }
    }



}