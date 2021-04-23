package com.ihsan.sona3

import android.app.Dialog
import android.content.Context
import androidx.fragment.app.Fragment


open class BaseFragment(fragment: Int) : Fragment(fragment) {
    lateinit var mDialog: Dialog
    fun showProgressDialog(context: Context) {
        mDialog = Dialog(context)
        mDialog.setContentView(R.layout.fragment_base)
        mDialog.show()
    }

    fun hideProgressDialog() {
        mDialog.dismiss()
    }
}