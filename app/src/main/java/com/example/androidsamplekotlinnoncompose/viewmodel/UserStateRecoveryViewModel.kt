package com.example.androidsamplekotlinnoncompose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

const val STATE_KEY_TEXT_NON_LIVE_DATA = "Key_Text_Non_Live_Data"
const val STATE_KEY_TEXT_LIVE_DATA = "Key_Text_Live_Data"

class UserStateRecoveryViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    //Coroutines
    //private lateinit var userMernJob: Job //若要把 Job 跟 Dispatchers 放置一起
    private var userMernJob =  Job() //若要把 Job 跟 Dispatchers 放置一起
    private var myScope = CoroutineScope(userMernJob + Dispatchers.Main)//共用一個 Scope


    //Test State
    //Non Live Data
    private val _tvStateNonLiveData1 = MediatorLiveData<String?>()
    val tvStateNonLiveData1: LiveData<String?> = _tvStateNonLiveData1
    // Live Data
    private val _btnStateLiveData = savedStateHandle.getLiveData<String?>("STATE_KEY_TEXT_LIVE_DATA")


    //Non LiveData set
    fun setStateText(inputData: String) {
//        savedStateHandle.set(STATE_KEY_TEXT, inputData)
        _tvStateNonLiveData1.value = inputData
        savedStateHandle[STATE_KEY_TEXT_NON_LIVE_DATA] = inputData
    }

    //Non LiveData get
    fun getStateText(): String? {
//        return savedStateHandle.get(STATE_KEY_TEXT)
        return savedStateHandle[STATE_KEY_TEXT_NON_LIVE_DATA]
    }

    fun getBtnState():MutableLiveData<String?> {
        return _btnStateLiveData
    }



    fun cancelJobOrAsync() {
//        userJob?.cancel()
//        userMernJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJobOrAsync()
    }
}