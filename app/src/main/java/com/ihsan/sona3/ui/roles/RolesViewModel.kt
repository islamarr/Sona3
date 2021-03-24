package com.ihsan.sona3.ui.roles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RolesViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is roles Fragment"
    }
    val text: LiveData<String> = _text
}