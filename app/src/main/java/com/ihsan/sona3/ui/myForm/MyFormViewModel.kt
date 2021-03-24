package com.ihsan.sona3.ui.myForm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyFormViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is form Fragment"
    }
    val text: LiveData<String> = _text
}