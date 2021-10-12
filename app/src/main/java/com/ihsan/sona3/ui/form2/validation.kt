/*
 * Last modified 10/10/21 3:22 PM
 */

package com.ihsan.sona3.ui.form2

import android.widget.EditText

class validation {
    companion object{
     fun validateUsername(name: EditText): Boolean {
        val usernameInput: String = name.getText().toString().trim()
        return if (usernameInput.isEmpty()) {
            name.error = "Field can't be empty"
            false
        } else if (usernameInput.length > 15) {
            name.error = "Username too long"
            false
        } else {
            name.error = null
            true
        }
    }
    fun validateID(id: EditText): Boolean {
        val IdInput: String = id.getText().toString().trim()
        return if (IdInput.isEmpty()) {
            id.error = "Field can't be empty"
            false
        } else if (IdInput.length !== 14) {
            id.error = "id must be 14 number"
            false
        } else {
            id.error = null
            true
        }
    }
    fun validatePhoneNumber(phoneNumber: EditText): Boolean {
        val phoneNumberInput: String = phoneNumber.getText().toString().trim()
        return if (phoneNumberInput.isEmpty()) {
            phoneNumber.error = "Field can't be empty"
            false
        } else if (phoneNumberInput.length !== 11) {
            phoneNumber.error = "id must be 14 number"
            false
        } else {
            phoneNumber.error = null
            true
        }
    }
//        fun validatenumoFQuran(NumQuran: EditText): Boolean {
//            val workInInput: Int = NumQuran.getText().trim()
//            return if (workInInput.isEmpty()){
//                NumQuran.error = "Field can't be empty"
//                false
//            }else {
//                NumQuran.error = null
//                true
//            }
//        }
        fun validateworkIn(workIn: EditText): Boolean {
            val workInInput: String = workIn.getText().toString().trim()
            return if (workInInput.isEmpty()) {
                workIn.error = "Field can't be empty"
                false
            }else {
                workIn.error = null
                true
            }
        }
        fun validateExecllent(workIn: EditText): Boolean {
            val workInInput: String = workIn.getText().toString().trim()
            return if (workInInput.isEmpty()) {
                workIn.error = "Field can't be empty"
                false
            }else {
                workIn.error = null
                true
            }
        }
    }
}