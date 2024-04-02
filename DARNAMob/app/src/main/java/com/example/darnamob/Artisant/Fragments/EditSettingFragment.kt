package com.example.darnamob.Artisant.Fragments

import android.annotation.SuppressLint
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
import android.widget.Button
import android.widget.EditText


class EditSettingFragment : Fragment() {

    private var myPreferences: SharedPreferences? = null
    private var myEditor: SharedPreferences.Editor? = null

    var switch1_status: Boolean = false
    var switch2_status: Boolean = false

    //shared preferencename
    private val my_prefs = "switch_prefs"

    private val state1 = "switch1"
    private val state2 = "switch2"


    //@SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.edit_settings_art, container, false)

        //to get the inputed text in the account fragment pfiuu.....
        val multiautoCompletetextview: MultiAutoCompleteTextView = view.findViewById(R.id.multiAutoCompleteTextView)
        val button: Button = view.findViewById(R.id.art_account)
        val art_mail: EditText = view.findViewById(R.id.art_mail)
        val art_phone: EditText = view.findViewById(R.id.art_phone)
        val art_workhours: EditText = view.findViewById(R.id.art_workhours)
        val status:Switch? = view?.findViewById(R.id.status)
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



        return view
    }
}
