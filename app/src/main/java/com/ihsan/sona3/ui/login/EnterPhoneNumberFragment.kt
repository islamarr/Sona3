package com.ihsan.sona3.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.databinding.FragmentEnterPhoneNumberBinding


class EnterPhoneNumberFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentEnterPhoneNumberBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        binding.btnSendCode.setOnClickListener(this)

        (activity as MainActivity).setHomeItemsVisibility(
            View.INVISIBLE,
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSendCode -> navController.navigate(R.id.action_enterPhoneNumberFragment_to_verificationFragment)
        }
    }


}