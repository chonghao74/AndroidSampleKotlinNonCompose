package com.example.androidsamplekotlinnoncompose.viewmodel

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val ITEM_FIRST_TEXT_KEY = "itemFirst"
const val ITEM_SECOND_TEXT_KEY = "itemSecond"
const val ITEM_THIRD_TEXT_KEY = "itemThird"
const val ITEM_FOURTH_TEXT_KEY = "itemFourth"
//const val ITEM_FIRST_TEXT_MENU_ITEM_KEY = "itemFirstMenuItem"

class ActivityNavigationCommonViewModel (private val savedStateHandle: SavedStateHandle) : ViewModel() {
    //UI MenuItem
    private val _itemFirstDataMenuItem = MutableLiveData<MenuItem?>()
    val itemFirstDataMenuItem : LiveData<MenuItem?> = _itemFirstDataMenuItem
    //State Recovery
    private val _itemFirstData = MutableLiveData<Int?>()
    val itemFirstData : LiveData<Int?> = _itemFirstData
    private val _itemSecondData = MutableLiveData<Int?>()
    val itemSecondData : LiveData<Int?> = _itemSecondData
    private val _itemThirdData = MutableLiveData<Int?>()
    val itemThirdData : LiveData<Int?> = _itemThirdData
    private val _itemFourthData = MutableLiveData<Int?>()
    val itemFourthData : LiveData<Int?> = _itemFourthData

//    fun recordeItemFirstMenuItem(destinationId: Int?) {
//        _itemFirstData.value = destinationId
//        savedStateHandle[ITEM_FIRST_TEXT_MENU_ITEM_KEY] = destinationId
//    }
//
//    fun getItemFirstMenuItem():MenuItem? {
//        val itemData = savedStateHandle.get<MenuItem?>(ITEM_FIRST_TEXT_MENU_ITEM_KEY)
////        return savedStateHandle[ITEM_ONE_TEXT_KEY]
//        return itemData
//    }

    fun recordeItemFirstId(destinationId: Int?) {
        _itemFirstData.value = destinationId
        savedStateHandle[ITEM_FIRST_TEXT_KEY] = destinationId
    }

    fun getItemFirstId():Int? {
        val itemData = savedStateHandle.get<Int>(ITEM_FIRST_TEXT_KEY)
//        return savedStateHandle[ITEM_ONE_TEXT_KEY]
        return itemData
    }

    fun recordeItemSecondId(destinationId: Int) {
        _itemFirstData.value = destinationId
        savedStateHandle[ITEM_SECOND_TEXT_KEY] = destinationId
    }

    fun getItemSecondId():Int? {
        val itemData = savedStateHandle.get<Int>(ITEM_SECOND_TEXT_KEY)
        return itemData
    }

    fun recordeItemThirdId(destinationId: Int?) {
        _itemFirstData.value = destinationId
        savedStateHandle[ITEM_THIRD_TEXT_KEY] = destinationId
    }

    fun getItemThirdId():Int? {
        val itemData = savedStateHandle.get<Int?>(ITEM_THIRD_TEXT_KEY)
        return itemData
    }

    fun recordeItemFourthId(destinationId: Int?) {
        _itemFirstData.value = destinationId
        savedStateHandle[ITEM_FOURTH_TEXT_KEY] = destinationId
    }

    fun getItemFourthId():Int? {
        val itemData = savedStateHandle.get<Int?>(ITEM_FOURTH_TEXT_KEY)
        return itemData
    }
}