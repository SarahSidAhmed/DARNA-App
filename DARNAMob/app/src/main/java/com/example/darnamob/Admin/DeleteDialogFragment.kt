package com.example.darnamob.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.R

class DeleteDialogFragment : DialogFragment() {
    private lateinit var db : DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments ?: id
        var rootView: View = inflater.inflate(R.layout.card_confirmdelete, container,  false)
        rootView.findViewById<Button>(R.id.no).setOnClickListener {
            dismiss()
        }
        rootView.findViewById<Button>(R.id.yes).setOnClickListener {
            db.banishUser(id)
            dismiss()
//            val dialog2 = SucDeleteDialogFragment()
//            dialog2.show(childFragmentManager,"customDialog")
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<Button>(R.id.no)
    }
}