package com.ihsan.sona3.ui.myForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ihsan.sona3.R
import com.ihsan.sona3.ui.main.MainActivity

class MyFormFragment : Fragment() {

    private lateinit var myFormViewModel: MyFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myFormViewModel =
            ViewModelProvider(this).get(MyFormViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_form, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        myFormViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity
        activity.binding.appBar.toolbar.title = getString(R.string.my_form)
    }
}