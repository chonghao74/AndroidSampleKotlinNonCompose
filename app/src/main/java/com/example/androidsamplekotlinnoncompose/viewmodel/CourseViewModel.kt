package com.example.androidsamplekotlinnoncompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsamplekotlinnoncompose.repository.CourseRepository
import com.example.androidsamplekotlinnoncompose.repository.model.course.CourseResponse
import com.example.androidsamplekotlinnoncompose.repository.model.user.LocalUser
import com.example.androidsamplekotlinnoncompose.repository.model.user.LocalUserValNull
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CourseViewModel : ViewModel() {
    private var courseMernJob: Job? = null
    private var courseRepository = CourseRepository()
    private val tokenPrefix = "Bearer"

    fun getCourse(token: String? = "") {

        courseMernJob = viewModelScope.launch {
            var json: CourseResponse? = null
            runCatching {
                withContext(Dispatchers.IO) {
                    json = courseRepository.getCourseByGson("$tokenPrefix ${token!!}")
                    Log.i("getCourse", "getCourse: runCatching")
                }
            }
                .onSuccess {
                    Log.i("success", "onSuccess: $it")
                }
                .onFailure {
                    Log.i("failure", "onFailure: $it")
                }
        }
    }

    fun testGsonAndMoshiSerialized() {

        val localUserJsonStrOne = Gson().toJson(LocalUser())
        val localJsonStrEG1 = "{\"age\":18,\"name\":\"Default\"}";
//        val localJsonStrEG1 = "{\"age\":null,\"name\":\"null\"}";
//        val localJsonStrEG1 = "{\"name\":\"null\"}";


        try {
            //序列化
            val localUserByGson = Gson().fromJson(localJsonStrEG1, LocalUserValNull::class.java)
            //反序列化
            val localUserJsonStrByGson = Gson().toJson(localUserByGson)

            val stop ="Strop"
        }
        catch (e:Exception){
            Log.i("GsonError", "testGsonAndMoshiSerialized: $e")
        }



        try {
            //Moshi ini
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory()) //使用 Kotlin 反射
                .build()
            val jsonAdapter = moshi.adapter(LocalUserValNull::class.java)

            //序列化
            val localUserByMoshi = jsonAdapter.fromJson(localJsonStrEG1)

            //反序列化
            val localUserJsonStrByMoshi = jsonAdapter.toJson(localUserByMoshi)

            val stop ="Strop"
        }
        catch (e:Exception){
            Log.i("MoshiError", "testGsonAndMoshiSerialized: $e")
        }









    }

    fun cancelJobOrAsync() {
        courseMernJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJobOrAsync()
    }
}