package com.example.darnamob.Client.Fragments


import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.darnamob.Client.Notifications
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AccountFragment : Fragment() {

    private  var userId: Int = 0
    private lateinit var db : DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_account, container, false)








        // Inflate the layout for this fragment
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("id", -1) ?: -1


        db = DatabaseHelper(requireContext())

        logic(userId)

    }

    private fun logic(userId: Int) {
        val member = db.getMembreByID(userId)

        view?.findViewById<TextView>(R.id.email)?.setText(member.email)
        view?.findViewById<TextView>(R.id.address)?.setText(member.address)
        view?.findViewById<TextView>(R.id.phone)?.setText(member.tel)
        view?.findViewById<TextView>(R.id.artisant_name)?.setText(member.userName)

        var image = member.image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)


        view?.findViewById<ImageView>(R.id.art_profil_pic)?.setImageBitmap(bitmap)



        view?.findViewById<ImageView>(R.id.notif)?.setOnClickListener {
            val intent = Intent(requireActivity(), Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }


    }


}