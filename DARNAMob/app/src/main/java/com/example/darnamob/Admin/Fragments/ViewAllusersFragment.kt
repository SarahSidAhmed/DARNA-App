package com.example.darnamob.Admin.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.darnamob.Admin.HomeAdmin
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R

private lateinit var db:DatabaseHelper

class ViewAllusersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_view_allusers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DatabaseHelper(requireContext())

        logic()
    }


    //put the logic here
    private fun logic() {
        val allUsers = db.getAllUsers()  //list of all the users use it in the recyclerview
        val reportedUsers = db.reportedUsers() //list of reported users ordered

        //for the search bar you got this function here
        //db.searchUserByName(userName)  user this when the search bar detects enter

        view?.findViewById<ImageView>(R.id.backArrow)?.setOnClickListener {
            val intent = Intent(requireContext(), HomeAdmin::class.java)
            startActivity(intent)

        }
    }

}