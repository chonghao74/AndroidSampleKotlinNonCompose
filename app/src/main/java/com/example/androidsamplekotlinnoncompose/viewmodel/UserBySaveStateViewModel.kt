package com.example.androidsamplekotlinnoncompose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.androidsamplekotlinnoncompose.repository.model.user.LocalUser
import com.example.androidsamplekotlinnoncompose.repository.model.user.UserTestResponse

const val STATE_KEY_TEXT_USER= "Key_Text_Non_User"
const val STATE_KEY_TEXT_USER_LIST= "Key_Text_Non_User_List"

class UserBySaveStateViewModel(private val savedStateHandle: SavedStateHandle):ViewModel() {
    private val _user = MediatorLiveData<LocalUser>() //確保資料設定都是由 ViewModel 內部調整
    val user: LiveData<LocalUser> = _user //此功能為取資料用
    private val _user2List = MediatorLiveData<List<UserTestResponse>>()
    val user2List: LiveData<List<UserTestResponse>> = _user2List //此功能為取資料用


    fun getUser(): LocalUser? {
        return user.value
    }

    fun getUserRecover(): LocalUser? {
        val useLiveData = savedStateHandle.getLiveData<LocalUser>(STATE_KEY_TEXT_USER)
//        val useStateFlow = savedStateHandle.getStateFlow(STATE_KEY_TEXT_USER, LocalUser())

        Log.e("UserBySaveStateViewModel get", "${useLiveData.value?.age}")
        return savedStateHandle[STATE_KEY_TEXT_USER]
    }

    fun setUser(user: LocalUser) {
        _user.value = user
        Log.e("UserBySaveStateViewModel set", "${user.age}")
        savedStateHandle[STATE_KEY_TEXT_USER] = user
    }

}