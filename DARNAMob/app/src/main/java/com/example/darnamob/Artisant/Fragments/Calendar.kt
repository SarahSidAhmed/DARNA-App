package com.example.darnamob.Artisant.Fragments
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import com.example.darnamob.Artisant.Notifications
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import com.example.darnamob.R.layout.calendar


private var userId : Int = -1


class Calendar : Fragment() {
    private lateinit var db: DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(calendar, container, false)
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

        val Tasks = db.getTasksArtisan(userId)
        val num = Tasks.size
        val calendar = view?.findViewById<CalendarView>(R.id.calendarView)

        if (num==0){
            view?.findViewById<ImageView>(R.id.noImage)?.visibility =  View.VISIBLE
            view?.findViewById<TextView>(R.id.noText)?.visibility =  View.VISIBLE

        }else{
            view?.findViewById<ImageView>(R.id.noImage)?.visibility =  View.GONE
            view?.findViewById<TextView>(R.id.noText)?.visibility =  View.GONE
        }


        view?.findViewById<ImageView>(R.id.notif)?.setOnClickListener {
            val intent = Intent(requireContext(), Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }
    }


}
