package com.example.darnamob.Artisant.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import com.example.darnamob.Artisant.MainActivityArtisant
import com.example.darnamob.Artisant.Notifications
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.R
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream
import java.io.IOException


private var myPreferences: SharedPreferences? = null
private var myEditor: SharedPreferences.Editor? = null

private var switch1_status = false
private var switch2_status = false

//shared preferencename

private val state1 = "switch1"
private val state2 = "switch2"

private var userId: Int =-1
private lateinit var db : DatabaseHelper
private lateinit var artisan : Artisan
private lateinit var workhour: String

class EditSettingFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_settings_art)

        db = DatabaseHelper(this)
        userId = intent.getIntExtra("id", -1)
        logic()
    }






     fun logic() {

        //to get the inputed text in the account fragment pfiuu.....


        val status: Switch? = findViewById(R.id.disponible)
        val deplacement: Switch? = findViewById(R.id.deplacement)
        //val status1:Switch? = view.findViewById(status1)


        myPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
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


        val actv1: MultiAutoCompleteTextView = findViewById(R.id.wilaya)
        val imageview: ImageView = findViewById(R.id.arrow)
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
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, wilayas)
        actv1.setAdapter(adapter)
        actv1.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        imageview.setOnClickListener {
            actv1.showDropDown()
        }



         artisan = db.getArtisanByID(userId)
         val workdays = db.getWorkDays(userId)
         workhour = db.getWorkHour(userId)


         //setting the data of the artisan
         val image = artisan.membre.image
         val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
         findViewById<ImageView>(R.id.art_profil_pic)?.setImageBitmap(bitmap)

         findViewById<TextView>(R.id.artisant_name)?.setText(artisan.membre.userName)
         findViewById<TextView>(R.id.art_mail)?.setText(artisan.membre.email)
         findViewById<EditText>(R.id.phoneNumber)?.setText(artisan.membre.tel)
         findViewById<EditText>(R.id.art_workhoursArtisan)?.setText(workhour)

         findViewById<MultiAutoCompleteTextView>(R.id.wilaya)?.setText(artisan.work_Area)


         //array of the toggles
         var toggles = arrayOf(
             findViewById<ToggleButton>(R.id.sat),
             findViewById<ToggleButton>(R.id.sun),
             findViewById<ToggleButton>(R.id.mon),
             findViewById<ToggleButton>(R.id.Tue),
             findViewById<ToggleButton>(R.id.wed),
             findViewById<ToggleButton>(R.id.thu),
             findViewById<ToggleButton>(R.id.fri)
         )

         val colorStateListBlue = ContextCompat.getColorStateList(this, R.color.my_custom_blue)
         val colorStateListWhite = ContextCompat.getColorStateList(this, R.color.white)


         val blue = Color.BLUE

         val blueColor = Color.BLUE

         for (toggle in toggles) {
             if (toggle.isChecked) {
                 toggle.setBackgroundColor(blueColor)
             }
         }








         //setting the image
         findViewById<ImageView>(R.id.camera)?.setOnClickListener {
             ImagePicker.with(this)
                 .crop()	    			//Crop image(Optional), Check Customization for more option
                 .compress(1024)			//Final image size will be less than 1 MB(Optional)
                 .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                 .start()
         }

         //the notification icon
         findViewById<ImageView>(R.id.notif)?.setOnClickListener {
             val intent = Intent(this, Notifications::class.java)
             intent.putExtra("id",userId)
             startActivity(intent)
         }


         findViewById<Button>(R.id.save)?.setOnClickListener {




             //getting the final states of the data
             val workArea = findViewById<MultiAutoCompleteTextView>(R.id.wilaya).text.toString()
             val workHours = findViewById<TextView>(R.id.art_workhoursArtisan).text.toString()
             val deplacement = findViewById<Switch>(R.id.deplacement).isChecked
             val disponible = findViewById<Switch>(R.id.disponible).isChecked
             val phone = findViewById<EditText>(R.id.phoneNumber).text.toString()

             //putting the bool workdays in a list
             val workDays = listOf<Int>(
                 if (findViewById<ToggleButton>(R.id.sat)?.isChecked == true)  1 else 0,
                 if (findViewById<ToggleButton>(R.id.sun)?.isChecked ==true)1 else 0,
                 if (findViewById<ToggleButton>(R.id.mon)?.isChecked == true) 1 else 0,
                 if (findViewById<ToggleButton>(R.id.Tue)?.isChecked == true) 1 else 0,
                 if (findViewById<ToggleButton>(R.id.wed)?.isChecked == true) 1 else 0,
                 if (findViewById<ToggleButton>(R.id.thu)?.isChecked == true) 1 else 0,
                 if (findViewById<ToggleButton>(R.id.fri)?.isChecked == true) 1 else 0
             )

             //into the db
             if (workArea?.isNotEmpty() == true && workHours?.isNotEmpty() == true) {
                 db.editPorfileArtisan(
                     userId,
                     workArea,
                     workHours,
                     deplacement,
                     disponible,
                     workDays,
                     image
                 )

                 db.editProfileMember(userId, phone, workArea, image)
                 val intent = Intent(this, MainActivityArtisant::class.java)
                 intent.putExtra("id", userId)
                 startActivity(intent)
             }else Toast.makeText(this, "Fields can not be empty, check your infomation.", Toast.LENGTH_SHORT).show()



         }



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        findViewById<ImageView>(R.id.art_profil_pic)?.setImageURI(data?.data)

        findViewById<Button>(R.id.save)?.setOnClickListener {


            val imageUri = data?.data //getting the image
            try {
                // Convert image URI to byte array
                val inputStream = this.contentResolver.openInputStream(imageUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArray = bitmapToByteArray(bitmap)


                //getting the final states of the data
                val workArea = findViewById<MultiAutoCompleteTextView>(R.id.wilaya)?.text?.toString()
                val workHours = findViewById<TextView>(R.id.art_workhoursArtisan).text.toString()
                val deplacement = findViewById<Switch>(R.id.deplacement).isChecked
                val disponible = findViewById<Switch>(R.id.disponible).isChecked
                val number = findViewById<EditText>(R.id.phoneNumber).text.toString()

                Toast.makeText(this, number, Toast.LENGTH_SHORT).show()

                //putting the bool workdays in a list
                val workDays = listOf<Int>(
                    if (findViewById<ToggleButton>(R.id.sat)?.isChecked == true)  1 else 0,
                    if (findViewById<ToggleButton>(R.id.sun)?.isChecked ==true)1 else 0,
                    if (findViewById<ToggleButton>(R.id.mon)?.isChecked == true) 1 else 0,
                    if (findViewById<ToggleButton>(R.id.Tue)?.isChecked == true) 1 else 0,
                    if (findViewById<ToggleButton>(R.id.wed)?.isChecked == true) 1 else 0,
                    if (findViewById<ToggleButton>(R.id.thu)?.isChecked == true) 1 else 0,
                    if (findViewById<ToggleButton>(R.id.fri)?.isChecked == true) 1 else 0
                )

                //into the db
                if (workArea?.isNotEmpty() == true && workHours?.isNotEmpty() == true) {
                    db.editPorfileArtisan(
                        userId,
                        workArea,
                        workHours,
                        deplacement,
                        disponible,
                        workDays,
                        byteArray
                    )

                    db.editProfileMember(userId, number, workArea, byteArray)
                    val intent = Intent(this, MainActivityArtisant::class.java)
                    intent.putExtra("id", userId)
                    startActivity(intent)
                }else Toast.makeText(this, "Fields can not be empty, check your infomation.", Toast.LENGTH_SHORT).show()

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