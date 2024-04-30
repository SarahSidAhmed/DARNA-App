package com.example.darnamob.Client


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.darnamob.Artisant.Fragments.Calendar
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.data.Demande
import com.example.darnamob.R
import com.example.darnamob.systems.pricingSystem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Locale

class AddNewOrderActivity : AppCompatActivity() {
    private lateinit var autoCompleteTxtCategory: AutoCompleteTextView
    private lateinit var autoCompleteTxtService: AutoCompleteTextView
    private lateinit var autoCompleteTxtRegion: AutoCompleteTextView
    private lateinit var db: DatabaseHelper
    private  var price =0
    private val calendar = java.util.Calendar.getInstance()
    private lateinit var dateSelector: EditText
    private lateinit var timeSelector: EditText
    private var hour= 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_order)


        //date selector
        dateSelector = findViewById<EditText>(R.id.editTextDate)

        dateSelector.setOnClickListener {
            showDatePicker()
        }

        //time selector
        timeSelector = findViewById(R.id.editTextTime)
        timeSelector.setOnClickListener {
            showTimePicker()
        }


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





        val day = findViewById<EditText>(R.id.editTextDate).text.toString().trim()

//

        val dialog = Dialog(this)
//        findViewById<Button>(R.id.checkPrice).setOnClickListener {
//            dialog.setContentView(R.layout.activity_estimated_price)
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        }


        findViewById<Button>(R.id.checkPrice).setOnClickListener {
            //the pop up here
            // if confirm put this

            price = 0

            autoCompleteTxtCategory.setAdapter(categoryAdapter)
            autoCompleteTxtRegion.setAdapter(regionAdapter)

            var selectedCategory: String
            // Set listener for c ategory selection
            autoCompleteTxtCategory.setOnItemClickListener { _, _, position, _ ->
                selectedCategory = categories[position]
                // Retrieve and populate services for the selected category
                populateServiceAutoComplete(selectedCategory)

            }

            selectedCategory = autoCompleteTxtCategory.text.toString().trim()
            autoCompleteTxtService.setOnClickListener {
                populateServiceAutoComplete(selectedCategory)
            }
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

            price = db.getPrestationPrice(service)

            price += pricingSystem().isNight(hour) + pricingSystem().isFerie(date)+ pricingSystem().getUrgentPrice(urgent)

            val demande = Demande(0, userId, title, description, region, address, categorie, service, date ,time, urgent, material)


            var dialog = EstimatedPriceDialog()
            dialog.show(supportFragmentManager, "customDialog")

        //db.addDemande(demande)

        }
    }


    //METHODS==============================================
    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog.OnTimeSetListener{TimePicker, hour, minute ->
            calendar.set(java.util.Calendar.HOUR_OF_DAY, hour)
            calendar.set(java.util.Calendar.MINUTE, minute)
            val formattedTime = SimpleDateFormat("HH:mm").format(calendar.time)
            timeSelector.setText(formattedTime)
        }
        hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        TimePickerDialog(this, timePickerDialog, calendar.get(java.util.Calendar.HOUR_OF_DAY), calendar.get(java.util.Calendar.MINUTE), true).show()
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->

            val selectedDate = java.util.Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            dateSelector.setText(formattedDate)
        },
        calendar.get(java.util.Calendar.YEAR),
        calendar.get(java.util.Calendar.MONTH),
        calendar.get(java.util.Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    private fun populateServiceAutoComplete(category: String) {
        // Retrieve services for the selected category from the database
        val servicesForCategory = db.getPrestationbyDomain(category)
        // Populate service AutoCompleteTextView with retrieved services
        val serviceNames = servicesForCategory.map { it.prestat }.toTypedArray()
        val serviceAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, serviceNames)
        autoCompleteTxtService.setAdapter(serviceAdapter)
    }



}


