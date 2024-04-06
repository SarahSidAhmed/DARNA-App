package com.example.darnamob.Artisant.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.darnamob.R
import android.widget.MultiAutoCompleteTextView
import com.example.darnamob.Database.DatabaseHelper

class AccountFragment : Fragment() {

    private var userId: Int =-1
    private lateinit var db : DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
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


    }

}


