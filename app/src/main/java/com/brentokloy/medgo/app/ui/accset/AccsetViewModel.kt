package com.brentokloy.medgo.app.ui.accset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AccsetViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is AccSet Fragment"
    }
    val text: LiveData<String> = _text
}