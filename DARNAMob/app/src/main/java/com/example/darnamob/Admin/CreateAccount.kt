package com.example.darnamob.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.R
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.Spinner
import android.widget.Toast

import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.Database.data.Membre

import com.example.darnamob.databinding.ActivityAddnewuserBinding

import com.example.darnamob.imageFromDrawableToByteArray
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


private lateinit var binding : ActivityAddnewuserBinding
private lateinit var db : DatabaseHelper
private lateinit var auth: FirebaseAuth

class CreateAccount : AppCompatActivity() {
    val categories = arrayOf("Painter", "Plumber", "Cleaner", "Maconier", "Electrician")
    val wilayas = arrayOf(
        "Adrar", "Chlef", "Laghouat", "Oum El Bouaghi", "Batna", "Béjaïa", "Biskra", "Béchar",
        "Blida", "Bouira", "Tamanrasset", "Tébessa", "Tlemcen", "Tiaret", "Tizi Ouzou", "Alger Est","Alger west",
        "Djelfa", "Jijel", "Sétif", "Saïda", "Skikda", "Sidi Bel Abbès", "Annaba", "Guelma",
        "Constantine", "Médéa", "Mostaganem", "M'Sila", "Mascara", "Ouargla", "Oran", "El Bayadh",
        "Illizi", "Bordj Bou Arréridj", "Boumerdès", "El Tarf", "Tindouf", "Tissemsilt", "El Oued",
        "Khenchela", "Souk Ahras", "Tipaza", "Mila", "Aïn Defla", "Naâma", "Aïn Témouchent", "Ghardaïa",
        "Relizane", "El M'ghair", "El Menia", "Ouled Djellal", "Bordj Baji Mokhtar", "Beni Abbes",
        "Timimoun", "Touggourt", "Djanet", "In Guezzam"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddnewuserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = com.google.firebase.Firebase.auth

       db = DatabaseHelper(this)
        var category : String = ""
        var wilaya : String = ""
        val spinner = findViewById<Spinner>(R.id.spinner1)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               category = categories[position]
               //Toast.makeText(applicationContext,categories[position],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Choose a category please", Toast.LENGTH_SHORT).show()
            }

        }
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val arrayAdapter2 = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,wilayas)
        spinner2.adapter = arrayAdapter2
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                wilaya = wilayas[position]
                //Toast.makeText(applicationContext,categories[position],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Choose a region please", Toast.LENGTH_SHORT).show()
            }

        }

        binding.createaccountButtom.setOnClickListener {
            createaccount(category,wilaya)

        }



        binding.back.setOnClickListener {
            startActivity(Intent(this, HomeAdmin::class.java))
            finish()
        }


    }

    private fun createaccount(category: String,wilaya : String) {
        val userName = binding.fullNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirm = binding.passwordConfirmEditText.text.toString()
        val domain = category
        val region = wilaya

        if (email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty() && userName.isNotEmpty()) {
            val drawable_name = "initprofile"
            val resId = resources.getIdentifier(drawable_name, "drawable", "com.example.darnamob")

            if (resId != 0) {
                val image =
                    imageFromDrawableToByteArray(this, resId) //converting image to ByteArray

                val checkExist = db.checkEmail(email)
                if (checkExist) Toast.makeText(
                    this,
                    "User already exists!",
                    Toast.LENGTH_SHORT
                ).show()
                else {//user doesn't exist
                    if (password == confirm) {

                        db.insertArtisan(Artisan(
                            Membre(0, phone, "", email, password, userName, image, 0),
                            domain,"",true,true,0f,region)
                        )
//                        val window = PopupWindow(this)
//                        val view  = layoutInflater.inflate(R.layout.activity_addedaccount,null)
//                        window.contentView = view
//                        val button = view.findViewById<Button>(R.id.button)
//                        button.setOnClickListener {
//                            startActivity(Intent(this, HomeAdmin::class.java))
//                            finish()
//                        }
                        Toast.makeText(this, "Created Account", Toast.LENGTH_SHORT).show()
                    }
                    else Toast.makeText(this, "Check your password", Toast.LENGTH_SHORT).show()
                }
            } else Toast.makeText(this, "Something happened. Try again.", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, "Fields can not be empty. Check you information again", Toast.LENGTH_SHORT).show()




    }
}