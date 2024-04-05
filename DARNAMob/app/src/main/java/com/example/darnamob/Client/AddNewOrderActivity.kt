package com.example.darnamob.Client


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R

class AddNewOrderActivity : AppCompatActivity() {
    private lateinit var autoCompleteTxtCategory: AutoCompleteTextView
    private lateinit var autoCompleteTxtService: AutoCompleteTextView
    private lateinit var autoCompleteTxtRegion: AutoCompleteTextView
    private lateinit var db: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_order)

        // Initialize views
        autoCompleteTxtCategory = findViewById(R.id.auto_complete_txt_category)
        autoCompleteTxtService = findViewById(R.id.auto_complete_txt_service)
        autoCompleteTxtRegion = findViewById(R.id.auto_complete_txt_region)

        // Initialize database helper
        db = DatabaseHelper(this)

        // Populate category AutoCompleteTextView
        val categories = arrayOf("Painting", "Plumbing", "Cleaning", "Masonry", "Electricity")
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
        /*autoCompleteTxtCategory.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = categories[position]
            // Retrieve and populate services for the selected category
            populateServiceAutoComplete(selectedCategory)
        }
    }*/

        /*  private fun populateServiceAutoComplete(category: String) {
              // Retrieve services for the selected category from the database
              val servicesForCategory = db.getPrestationbyDomain(category)
              // Populate service AutoCompleteTextView with retrieved services
              val serviceNames = servicesForCategory.map { it.prestat }.toTypedArray()
              val serviceAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, serviceNames)
              autoCompleteTxtService.setAdapter(serviceAdapter)
          }*/



    }

}
