package com.ihsan.sona3.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast

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