package com.example.darnamob.Artisant.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import com.example.darnamob.R
import com.example.darnamob.R.id
import com.example.darnamob.R.id.arrow
import com.example.darnamob.R.id.multiAutoCompleteTextView

class EditSettingFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val actv1: MultiAutoCompleteTextView = view.findViewById(multiAutoCompleteTextView)
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

        return view
    }
}
