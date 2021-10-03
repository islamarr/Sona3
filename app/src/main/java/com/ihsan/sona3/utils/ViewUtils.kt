package com.ihsan.sona3.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.ihsan.sona3.R

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ImageView.show() {
    visibility = View.VISIBLE
}

fun ImageView.hide() {
    visibility = View.INVISIBLE
}

fun expandCard(view: ViewGroup, txtView: TextView) {
    if (view.visibility == View.GONE) {
        TransitionManager.beginDelayedTransition(
            view, AutoTransition()
        )
        view.visibility = View.VISIBLE
        txtView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up, 0, 0, 0)

    } else {
        TransitionManager.beginDelayedTransition(
            view, AutoTransition()
        )
        view.visibility = View.GONE
        txtView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down,0,0,0)

    }
}