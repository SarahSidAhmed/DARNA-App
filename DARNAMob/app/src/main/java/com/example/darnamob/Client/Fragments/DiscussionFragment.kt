package com.example.darnamob.Client.Fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.darnamob.R

class DiscussionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discussion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userId: Int =-1
        arguments?.let { bundle ->
          userId = bundle.getInt("id", -1)
        }

            Toast.makeText(requireContext(), "$userId", Toast.LENGTH_SHORT).show()


    }
}

