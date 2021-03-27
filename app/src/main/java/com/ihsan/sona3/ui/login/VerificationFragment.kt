package com.ihsan.sona3.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.databinding.FragmentVerificationBinding


class VerificationFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentVerificationBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVerificationBinding.inflate(inflater, container, false)
        formatCode()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        binding.btnContinue.setOnClickListener(this)
        binding.tvUnsentCode.setOnClickListener(this)

        (activity as MainActivity).setHomeItemsVisibility(
            View.INVISIBLE,
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnContinue -> navController.navigate(R.id.action_verificationFragment_to_nav_home)
            R.id.tvUnsentCode -> navController.navigate(R.id.action_verificationFragment_to_incorrectNumberFragment)
        }

    }

    // this method was created to move from a edit text to another after writing the digit
    private fun formatCode() {
        binding.etDigit1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etDigit1.text.toString().length == 1) {
                    binding.etDigit1.clearFocus()
                    binding.etDigit2.requestFocus()
                    binding.etDigit2.isCursorVisible = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etDigit1.text.toString().isEmpty()) {
                    binding.etDigit1.requestFocus()
                }
            }
        })

        binding.etDigit2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etDigit2.text.toString().length == 1) {
                    binding.etDigit2.clearFocus()
                    binding.etDigit3.requestFocus()
                    binding.etDigit3.isCursorVisible = true

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {


                binding.etDigit2.setOnKeyListener { v, keyCode, event ->
                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                    if (keyCode == KeyEvent.KEYCODE_DEL) { //this is for backspace

                        binding.etDigit2.clearFocus()
                        binding.etDigit1.requestFocus()
                        binding.etDigit1.isCursorVisible = true

                    }
                    false
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etDigit2.text.toString().isEmpty()) {
                    binding.etDigit2.requestFocus()
                }

            }
        })
        binding.etDigit3.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etDigit3.text.toString().length == 1) {
                    binding.etDigit3.clearFocus()
                    binding.etDigit4.requestFocus()
                    binding.etDigit4.isCursorVisible = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etDigit3.text.toString().isEmpty()) {
                    binding.etDigit3.requestFocus()
                }

            }
        })
        binding.etDigit3.setOnKeyListener { v, keyCode, event ->
            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
            if (keyCode == KeyEvent.KEYCODE_DEL) { //this is for backspace

                binding.etDigit3.clearFocus()
                binding.etDigit2.requestFocus()
                binding.etDigit2.isCursorVisible = true

            }
            false
        }



        binding.etDigit4.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etDigit4.text.toString().length == 1) {
                    binding.etDigit4.clearFocus()
                    binding.etDigit5.requestFocus()
                    binding.etDigit5.isCursorVisible = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etDigit4.text.toString().isEmpty()) {
                    binding.etDigit4.requestFocus()
                }
            }
        })

        binding.etDigit4.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL) { //this is for backspace

                binding.etDigit4.clearFocus()
                binding.etDigit3.requestFocus()
                binding.etDigit3.isCursorVisible = true

            }
            false
        }

        binding.etDigit5.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etDigit5.text.toString().length == 1) {
                    binding.etDigit5.clearFocus()
                    binding.etDigit6.requestFocus()
                    binding.etDigit6.isCursorVisible = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etDigit5.text.toString().isEmpty()) {
                    binding.etDigit5.requestFocus()
                }
            }
        })

        binding.etDigit5.setOnKeyListener { v, keyCode, event ->
            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
            if (keyCode == KeyEvent.KEYCODE_DEL) { //this is for backspace

                binding.etDigit5.clearFocus()
                binding.etDigit4.requestFocus()
                binding.etDigit4.isCursorVisible = true

            }
            false
        }
        binding.etDigit6.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

                if (binding.etDigit6.text.toString().isEmpty()) {
                    binding.etDigit6.requestFocus()
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etDigit6.text.toString().length == 1) {
                    binding.etDigit6.clearFocus()
                    binding.btnContinue.requestFocus()
                    binding.etDigit6.isCursorVisible = true

                }
            }
        })

    }

}