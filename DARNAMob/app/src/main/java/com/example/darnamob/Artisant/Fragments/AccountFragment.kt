package com.example.darnamob.Artisant.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.darnamob.R
import android.widget.MultiAutoCompleteTextView

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val multiAutoCompleteTextView: MultiAutoCompleteTextView = view.findViewById(R.id.multiAutoCompleteTextView)
        val listItems = resources.getStringArray(R.array.list_items)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listItems)
        multiAutoCompleteTextView.setAdapter(adapter)
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }
}


