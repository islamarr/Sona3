package com.ihsan.sona3.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.databinding.SplashFragmentBinding


class SplashFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: SplashFragmentBinding
    private lateinit var navController: NavController


    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        binding.btnLogin.setOnClickListener(this)
        binding.tvSkip.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        (activity as MainActivity).setHomeItemsVisibility(View.INVISIBLE)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin -> navController.navigate(R.id.action_splashFragment_to_enterPhoneNumberFragment)
            R.id.tvSkip -> navController.navigate(R.id.action_splashFragment_to_nav_home)
        }
    }


}