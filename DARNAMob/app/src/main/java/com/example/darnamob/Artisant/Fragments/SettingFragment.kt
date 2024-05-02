package com.example.darnamob.Artisant.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.darnamob.Client.EditPasswordSettings
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R

class SettingFragment : Fragment() {

    private var userId: Int =-1
    private lateinit var db : DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
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
        view?.findViewById<ImageView>(R.id.notif)?.setOnClickListener {
            val intent = Intent(requireActivity(), com.example.darnamob.Artisant.Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }

        view?.findViewById<LinearLayout>(R.id.editProfile)?.setOnClickListener{
            val intent = Intent(requireActivity(), EditSettingFragment::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }

        view?.findViewById<LinearLayout>(R.id.editPass)?.setOnClickListener{
            val intent = Intent(requireContext(), EditPasswordSettings::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)

        }
    }


}