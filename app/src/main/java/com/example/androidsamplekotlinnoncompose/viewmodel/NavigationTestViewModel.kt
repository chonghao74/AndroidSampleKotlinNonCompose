package com.example.androidsamplekotlinnoncompose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val BTN_TEXT_KEY = "btnText"

class NavigationTestViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _btnText = MutableLiveData<String?>()
    val btnText : LiveData<String?> = _btnText




    fun setBtnText(text: String?) {
        _btnText.value = text
        savedStateHandle[BTN_TEXT_KEY] = text
    }

    fun getBtnText(): String? {
        return _btnText.value
    }

    fun getBtnTextLiveData(){
        _btnText.value = savedStateHandle[BTN_TEXT_KEY]
    }

    fun getBtnTextRecover(): String? {
        return savedStateHandle[BTN_TEXT_KEY]
    }
}