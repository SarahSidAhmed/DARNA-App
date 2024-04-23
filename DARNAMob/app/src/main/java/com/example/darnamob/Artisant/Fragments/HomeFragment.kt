package com.example.darnamob.Artisant.Fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.ContextMenu
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.darnamob.Database.data.Demande
import com.example.darnamob.Artisant.Home_adapter
import android.widget.AdapterView.OnItemClickListener
import com.example.darnamob.Admin.ViewAllUsers
import com.example.darnamob.Artisant.art_view_order
import com.example.darnamob.Database.data.Artisan


class HomeFragment : Fragment(),Home_adapter.OnItemClickListener {

    private var userId: Int =-1
   private lateinit var db : DatabaseHelper
   private lateinit var newRecyclerview : RecyclerView
   //private lateinit var demandes : List<Demande>




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

        userId =2
        // Initialize the database helper
        db = DatabaseHelper(requireContext())



//        newRecyclerview = view.findViewById(R.id.recycler)
//        newRecyclerview.layoutManager = LinearLayoutManager(requireContext())
//       // newRecyclerview?.layoutManager = LinearLayoutManager(requireContext())
//
//        (newRecyclerview.layoutManager as? LinearLayoutManager)?.orientation = LinearLayoutManager.VERTICAL
//
//        newRecyclerview.setHasFixedSize(true)
//        newRecyclerview.adapter = Home_adapter(demandes, com.example.darnamob.Artisant.Fragments.db, this)
//        registerForContextMenu(newRecyclerview)
//

        // Perform your logic here
        logic(userId)






    }

    private fun logic(userId: Int) {
        val artisan = db.getArtisanByID(userId)


        view?.findViewById<ImageView>(R.id.notif)?.setOnClickListener { val intent = Intent(requireActivity(), Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }
        val text = view?.findViewById<TextView>(R.id.art_name_hello)?.text
        view?.findViewById<TextView>(R.id.art_name_hello)?.setText("Welcome\n"+artisan.membre.userName)

        val image = artisan.membre.image

        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        view?.findViewById<ImageView>(R.id.art_profil_pic)?.setImageBitmap(bitmap)

        //this is the list of demandes put it in the adapter
        val demandes = db.getAllDemandeByRegionDispo(artisan.work_Area, artisan.disponible)


        view?.findViewById<EditText>(R.id.searchBar)?.setOnKeyListener { v, keyCode, event ->

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                Toast.makeText(requireContext(), "SET!!", Toast.LENGTH_SHORT).show()
                return@setOnKeyListener true
            }
            false


        }
//       newRecyclerview = view?.findViewById(R.id.recycler) ?:
//         newRecyclerview?.layoutManager = LinearLayoutManager(requireContext())

       newRecyclerview = view?.findViewById<RecyclerView>(R.id.recycler)!!
 newRecyclerview?.layoutManager = LinearLayoutManager(requireContext())
//         newRecyclerview = view?.findViewById<RecyclerView>(R.id.recycler)
//        newRecyclerview?.layoutManager = LinearLayoutManager(requireContext())


        (newRecyclerview.layoutManager as? LinearLayoutManager)?.orientation = LinearLayoutManager.VERTICAL

        newRecyclerview.setHasFixedSize(true)
        newRecyclerview.adapter = Home_adapter(demandes, com.example.darnamob.Artisant.Fragments.db, this)
       // registerForContextMenu(newRecyclerview)


//        lateinit var db : DatabaseHelper
//        lateinit var newRecyclerview : RecyclerView
//        lateinit var newList : List<Demande>
//
//        //newRecyclerview = view?.findViewById(R.id.recycler) ?:
//       // newRecyclerview.layoutManager = LinearLayoutManager(requireContext())
////        newRecyclerview.setHasFixedSize(true)
////        newRecyclerview.adapter = Home_adapter(demandes,
//// com.example.darnamob.Artisant.Fragments.db, this)
////        registerForContextMenu(newRecyclerview)
//
//         val myRecyclerView = view?.findViewById<RecyclerView>(R.id.recycler)
//        myRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
//        (myRecyclerView?.layoutManager as? LinearLayoutManager)?.orientation = LinearLayoutManager.VERTICAL
//
//        newRecyclerview.setHasFixedSize(true)
//        newRecyclerview.adapter = Home_adapter(demandes, com.example.darnamob.Artisant.Fragments.db, this)
//        registerForContextMenu(newRecyclerview)
//
//        //myRecyclerView.adapter=???

    }




    override fun onItemClick(demande: Demande) {


        // Start view art order
        val intent = Intent(context, art_view_order::class.java)

//            intent.putExtra("email", artisan.membre.email)
//            // Add more data if needed
//            startActivity(intent)}
//        val intent = Intent(this, ActivityProfileClient::class.java)
//            intent.putExtra("email", artisan.membre.email)
//            intent.putExtra("phone", artisan.membre.tel)
//            intent.putExtra("address", artisan.membre.address)
//            // Add more data if needed
//            startActivity(intent)}
    }

//    fun onCreateContextMenu(
//        menu: ContextMenu?,
//        v: View?,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
////        super.onCreateContextMenu(menu, v, menuInfo)
////        menu!!.setHeaderTitle("Select Below")
////        menu.add(0,v!!.id,0,"Report")
////        menu.add(0,v!!.id,0,"Ban")
//    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return true
        //super.onContextItemSelected(item)
//        if(item!!.title=="Ban"){
//            // db.banishUser(artisan.member.id)
//            val intent = Intent(this, BannedAccountActivity::class.java)
//            startActivity(intent)
//        }
//        if(item!!.title=="Report"){
//            val intent = Intent(this, ReportSentActivity::class.java)
//        }
    }




















}