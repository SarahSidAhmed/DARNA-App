package com.example.darnamob.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.darnamob.R

class SucDeleteDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.activity_deletedaccount, container,  false)
        rootView.findViewById<Button>(R.id.done).setOnClickListener {
            dismiss()
            val intent = Intent(requireContext(), ActivityViewAllusers::class.java)
            startActivity(intent)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<Button>(R.id.done)
    }
}