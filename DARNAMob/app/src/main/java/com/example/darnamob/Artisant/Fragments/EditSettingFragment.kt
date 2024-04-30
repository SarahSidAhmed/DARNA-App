package com.example.darnamob.Artisant.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import com.example.darnamob.R
import android.content.Context
import android.widget.Switch
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream
import java.io.IOException


class EditSettingFragment : Fragment() {

    private var myPreferences: SharedPreferences? = null
    private var myEditor: SharedPreferences.Editor? = null

    var switch1_status: Boolean = false
    var switch2_status: Boolean = false

    //shared preferencename
    private val my_prefs = "switch_prefs"

    private val state1 = "switch1"
    private val state2 = "switch2"

    private var userId: Int =-1
    private lateinit var db : DatabaseHelper
    private lateinit var artisan : Artisan
    private lateinit var workhour: String


    //@SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_settings_art, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            userId = bundle.getInt("id", -1)
        }

        //to get the inputed text in the account fragment pfiuu.....
        val multiautoCompletetextview: MultiAutoCompleteTextView = view.findViewById(R.id.multiAutoCompleteTextView)
        val button: Button = view.findViewById(R.id.save)
        val art_mail: EditText = view.findViewById(R.id.art_mail)
        val art_phone: EditText = view.findViewById(R.id.art_phone)
        val art_workhours: EditText = view.findViewById(R.id.art_workhours)
        val status:Switch? = view?.findViewById(R.id.disponible)
        val deplacement :Switch? = view?.findViewById(R.id.deplacement)
        //val status1:Switch? = view.findViewById(status1)


        myPreferences = activity?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        myEditor = myPreferences?.edit()

        switch1_status = myPreferences?.getBoolean(state1, false) ?: false

        switch2_status = myPreferences?.getBoolean(state2, false) ?: false

        status?.isChecked = switch1_status
        deplacement?.isChecked = switch2_status


        status?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myEditor?.putBoolean(state1, true)
                myEditor?.apply()
                status.isChecked = true
            } else {
                myEditor?.putBoolean(state1, false)
                myEditor?.apply()
                status.isChecked = false
            }
        }

        deplacement?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myEditor?.putBoolean(state2, true)
                myEditor?.apply()
                deplacement.isChecked = true
            } else {
                myEditor?.putBoolean(state2, false)
                myEditor?.apply()
                deplacement.isChecked = false
            }
        }



        val actv1: MultiAutoCompleteTextView = view.findViewById(R.id.multiAutoCompleteTextView)
        val imageview: ImageView = view.findViewById(R.id.arrow)
        actv1.threshold = 2
        // Add the remaining wilayas here
        val wilayas: List<String> = listOf(
            "Alger",
            "Alger Est",
            "Alger Ouest",
            "Adrar",
            "Chlef",
            "Laghouat",
            "Oum el-Bouaghi",
            "Batna",
            "Béjaïa",
            "Biskra",
            "Béchar",
            "Blida",
            "Bouira",
            "Tamanghasset",
            "Tébessa",
            "Tlemcen",
            "Tiaret",
            "Tizi Ouzou",
            "Algiers",
            "Djelfa",
            "Jijel",
            "Sétif",
            "Saïda",
            "Skikda",
            "Sidi Bel Abbes",
            "Annaba",
            "Guelma",
            "Constantine",
            "Médéa",
            "Mostaganem",
            "M'Sila",
            "Mascara",
            "ouargla",
            "Oran",
            "El Bayadh",
            "Illizi",
            "Bordj Bou Arréridj",
            "Boumerdès",
            "El Taref",
            "Tindouf",
            "Tissemsilt",
            "El Oued",
            "Khenchela",
            "Souk Ahras",
            "Tipasa",
            "Mila",
            "Aïn Defla",
            "Naama",
            "Aïn Témouchent",
            "Ghardaïa",
            "Relizane"
        )

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, wilayas)
        actv1.setAdapter(adapter)
        actv1.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        imageview.setOnClickListener {
            actv1.showDropDown()
        }


        button.setOnClickListener{
            val mail = art_mail.text.toString()
            val intent = Intent(view.context,AccountFragment::class.java)
            intent.putExtra("mail",mail)
            startActivity(intent)

            val phone = art_phone.text.toString()
            val intent1 = Intent(view.context,AccountFragment::class.java)
            intent1.putExtra("phone",phone)
            startActivity(intent1)

            val workhours = art_workhours.text.toString()
            val intent2 = Intent(view.context,AccountFragment::class.java)
            intent2.putExtra("workhours",workhours)
            startActivity(intent2)
        }

        // Initialize the database helper
        db = DatabaseHelper(requireContext())

        // Perform your logic here
        logic(userId)
    }

    private fun logic(userId: Int) {

        val blueColor = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.workdaysColor ))

        val white = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white ))
        artisan = db.getArtisanByID(userId)
        val workdays = db.getWorkDays(userId)
        workhour = db.getWorkHour(userId)


        //setting the data of the artisan
        val image = artisan.membre.image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        view?.findViewById<ImageView>(R.id.art_profil_pic)?.setImageBitmap(bitmap)

        view?.findViewById<ImageView>(R.id.art_profil_pic)?.setImageBitmap(bitmap)
        view?.findViewById<TextView>(R.id.artisant_name)?.setText(artisan.membre.userName)
        view?.findViewById<TextView>(R.id.art_mail)?.setText(artisan.membre.email)
        view?.findViewById<EditText>(R.id.art_phone)?.setText(artisan.membre.tel)
        view?.findViewById<EditText>(R.id.art_workhours)?.setText(workhour)

        view?.findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)?.setText(artisan.work_Area)


        //array of the toggles
        val toggles = arrayOf(
            view?.findViewById<ToggleButton>(R.id.sat),
            view?.findViewById<ToggleButton>(R.id.sun),
            view?.findViewById<ToggleButton>(R.id.mon),
            view?.findViewById<ToggleButton>(R.id.Tue),
            view?.findViewById<ToggleButton>(R.id.wed),
            view?.findViewById<ToggleButton>(R.id.thu),
            view?.findViewById<ToggleButton>(R.id.fri)
        )


        for ((i, item) in toggles.withIndex()){
            item?.isChecked = workdays[i]
            if (workdays[i]) item?.background = blueColor
            else item?.background = white
            item?.setOnClickListener{
                item?.let { it.isChecked = !it.isChecked }
            }
        }


        //setting the image
        view?.findViewById<ImageView>(R.id.camera)?.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        //the notification icon
        view?.findViewById<ImageView>(R.id.notif)?.setOnClickListener {
            val intent = Intent(requireContext(), Notifications::class.java)
            intent.putExtra("id",userId)
            startActivity(intent)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        view?.findViewById<ImageView>(R.id.art_profil_pic)?.setImageURI(data?.data)

        view?.findViewById<Button>(R.id.save)?.setOnClickListener {


            val imageUri = data?.data //getting the image
            try {
                // Convert image URI to byte array
                val inputStream = requireContext().contentResolver.openInputStream(imageUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArray = bitmapToByteArray(bitmap)


                //getting the final states of the data
                val workArea = view?.findViewById<EditText>(R.id.work_area)?.text?.toString()?.trim()
                val workHours = view?.findViewById<TextView>(R.id.workhours)?.text?.toString()?.trim()
                val deplacement = view?.findViewById<Switch>(R.id.deplacement)?.isChecked==true
                val disponible = view?.findViewById<Switch>(R.id.disponible)?.isChecked==true

                //putting the bool workdays in a list
                val workDays = listOf<Int>(
                    if (view?.findViewById<ToggleButton>(R.id.sat)?.isChecked == true) 1 else 0,
                    if (view?.findViewById<ToggleButton>(R.id.sun)?.isChecked ==true)1 else 0,
                    if (view?.findViewById<ToggleButton>(R.id.mon)?.isChecked == true) 1 else 0,
                    if (view?.findViewById<ToggleButton>(R.id.Tue)?.isChecked == true) 1 else 0,
                    if (view?.findViewById<ToggleButton>(R.id.wed)?.isChecked == true) 1 else 0,
                    if (view?.findViewById<ToggleButton>(R.id.thu)?.isChecked == true) 1 else 0,
                    if (view?.findViewById<ToggleButton>(R.id.fri)?.isChecked == true) 1 else 0
                )

                //into the db
                if (workArea?.isNotEmpty() == true && workHours?.isNotEmpty()==true) {
                    db.editPorfileArtisan(
                        userId,
                        workArea,
                        workHours,
                        deplacement,
                        disponible,
                        workDays,
                        byteArray
                    )
                }else Toast.makeText(requireContext(), "Fields can not be empty, check your infomation.", Toast.LENGTH_SHORT).show()

                // Use the byte array as needed
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //updating the data in the database
        }
    }


    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

}
