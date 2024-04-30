package com.example.darnamob.Client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.darnamob.R

class EstimatedPriceDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.activity_estimated_price, container,  false)
        rootView.findViewById<Button>(R.id.confirmBtn).setOnClickListener {
            //put the function here !!!
        }
        rootView.findViewById<Button>(R.id.cancelBtn).setOnClickListener {
            dismiss()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}