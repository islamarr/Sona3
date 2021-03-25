package com.ihsan.sona3.ui.login

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ihsan.sona3.R
import com.ihsan.sona3.databinding.FragmentIncorrectNumberBinding


class IncorrectNumberFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentIncorrectNumberBinding
    private lateinit var navController: NavController
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIncorrectNumberBinding.inflate(inflater, container, false)
        setWarningImageAnimation()
        setTimer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        binding.btnChangeNumber.setOnClickListener(this)
    }

    private fun setWarningImageAnimation() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_fade_out)
        binding.ivWarning.animation = animation
    }

    private fun setTimer() {
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.btnResend.isEnabled = false
                binding.btnResend.text = "اعد ارسال الرمز \n" +
                        "خلال ${millisUntilFinished / 1000} ثانية"
            }

            override fun onFinish() {
                binding.btnResend.isEnabled = true
                binding.btnResend.text = "اعد ارسال الرمز"
            }
        }
        timer.start()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnChangeNumber -> {
                navController.navigate(R.id.action_incorrectNumberFragment_to_enterPhoneNumberFragment)
                timer.cancel()

            }
        }
    }


}