package com.koko.www.androiddemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SimpleViewModel : ViewModel() {
    var name: String = "Koko Hekmatyar"
    private val changeUIs = MutableLiveData<String>()
    val name1: LiveData<String> = changeUIs


    fun onClick() {
        updateName()
    }

    private fun updateName() {
        name = "koko"
    }
}