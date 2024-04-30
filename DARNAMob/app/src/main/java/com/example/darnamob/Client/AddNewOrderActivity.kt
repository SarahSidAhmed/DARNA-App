package com.example.darnamob.Client


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.darnamob.Artisant.Fragments.Calendar
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Demande
import com.example.darnamob.R
import com.example.darnamob.systems.pricingSystem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewOrderActivity : AppCompatActivity() {
    private lateinit var autoCompleteTxtCategory: AutoCompleteTextView
    private lateinit var autoCompleteTxtService: AutoCompleteTextView
    private lateinit var autoCompleteTxtRegion: AutoCompleteTextView
    private lateinit var db: DatabaseHelper
    private  var price =0
    private lateinit var datePickerDialog: DatePickerDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_order)

        initDatePicker()
        findViewById<EditText>(R.id.editTextDate).setText(getTodaysDate())

        // Initialize views
        autoCompleteTxtCategory = findViewById(R.id.auto_complete_txt_category)
        autoCompleteTxtService = findViewById(R.id.auto_complete_txt_service)
        autoCompleteTxtRegion = findViewById(R.id.auto_complete_txt_region)

        // Initialize database helper
        val userId = 2 // intent.getIntExtra("id", -1)
        db = DatabaseHelper(this)

        //notification click
        findViewById<ImageView>(R.id.notifIcon).setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }

        //going back
        findViewById<ImageView>(R.id.back).setOnClickListener {
            val intent = Intent(this, MainActivityClient::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
            finish()
        }

        // Populate category AutoCompleteTextView
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

        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        val regionAdapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,wilayas)
        autoCompleteTxtCategory.setAdapter(categoryAdapter)
        autoCompleteTxtRegion.setAdapter(regionAdapter)

        // Set listener for category selection
        autoCompleteTxtCategory.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = categories[position]
            // Retrieve and populate services for the selected category
            populateServiceAutoComplete(selectedCategory)

        }
        if(findViewById<AutoCompleteTextView>(R.id.auto_complete_txt_service).text.toString().isNotEmpty()){
            Toast.makeText(this, findViewById<AutoCompleteTextView>(R.id.auto_complete_txt_service).text.toString(), Toast.LENGTH_SHORT).show()
        }
        //price = db.getPrestationPrice(findViewById<AutoCompleteTextView>(R.id.auto_complete_txt_service).text.toString().trim())



        //calculating the price
        val hour = findViewById<EditText>(R.id.editTextTime).text.toString().trim()
        val day = findViewById<EditText>(R.id.editTextDate).text.toString().trim()

        //getting only the day and the month of the date to check if it is a vacation day
        val jjmm = day.substring(0, minOf(day.length, 5)) //day.substring(0, minOf(day.length, 5)

        //adding the night price
        if (pricingSystem().isNight(hour)  ) price += pricingSystem().getnightPrice()
        //adding the vacation day price
        if (pricingSystem().isFerie(jjmm)) price += pricingSystem().getjourFeriePrice()
        //adding urgency fees
        if (findViewById<CheckBox>(R.id.urgent).isChecked) price += pricingSystem().getUrgentPrice()

        val dialog = Dialog(this)
//        findViewById<Button>(R.id.checkPrice).setOnClickListener {
//            dialog.setContentView(R.layout.activity_estimated_price)
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        }


        findViewById<Button>(R.id.checkPrice).setOnClickListener {
            //the pop up here
            // if confirm put this
            val title = findViewById<EditText>(R.id.editTextTitle).text.toString().trim()
            val description = findViewById<EditText>(R.id.editTextMoreDetails).text.toString().trim()
            val address = findViewById<EditText>(R.id.editTextAdress).text.toString().trim()
            val categorie = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt_category).text.toString().trim()
            val service = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt_service).text.toString().trim()
            val region = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt_region).text.toString().trim()
            val date = findViewById<EditText>(R.id.editTextDate).text.toString().trim()
            val time = findViewById<EditText>(R.id.editTextTime).text.toString().trim()
            val material = findViewById<CheckBox>(R.id.urgent).isChecked
            val urgent = findViewById<CheckBox>(R.id.material).isChecked


            val demande = Demande(0, userId, title, description, region, address, categorie, service, date ,time, urgent, material)
//            db.addDemande(demande)
            //to this
            val intent = Intent(this, EstimatedPrice::class.java)
            intent.putExtra("price", price)
            startActivity(intent)
            //if canceled just make the pop up disappear
        }
    }




    private fun getTodaysDate(): String {

        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        var month = calendar.get(java.util.Calendar.MONTH)
        month +=1
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        return makeDateString(day, month, year)
    }


    private fun populateServiceAutoComplete(category: String) {
        // Retrieve services for the selected category from the database
        val servicesForCategory = db.getPrestationbyDomain(category)
        // Populate service AutoCompleteTextView with retrieved services
        val serviceNames = servicesForCategory.map { it.prestat }.toTypedArray()
        val serviceAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, serviceNames)
        autoCompleteTxtService.setAdapter(serviceAdapter)
    }

    fun openDatePicker(view: View) {

        datePickerDialog.show()
    }
    private fun initDatePicker() {
        val datesetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            var month = month + 1
            val date = makeDateString(day, month, year)
            findViewById<EditText>(R.id.editTextDate).setText(date)
        }

        val calendar = java.util.Calendar.getInstance()
         val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val style = AlertDialog.THEME_HOLO_LIGHT
        val datePickerDialog = DatePickerDialog(
            this,
            style,
            datesetListener,
            year,
            month,
            day
        )


    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return getMonthFormat(month)+" "+day+" "+year
    }

    private fun getMonthFormat(month: Int): String {

        val months = arrayOf(
            "JAN",
            "FEB",
            "MAR",
            "APR",
            "MAY",
            "JUN",
            "JUL",
            "AUG",
            "SEP",
            "OCT",
            "NOV",
            "DEC"
        )
        return months[month]
    }


}


