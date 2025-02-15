package com.example.androidsamplekotlinnoncompose.repository.model.course

import com.example.androidsamplekotlinnoncompose.repository.model.course.Data
import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("code")
    val code:Int = 999,
    @SerializedName("data")
    val data: Data
)

data class Data(
    val result: String,
    val message: List<Course>,
)

data class Course(
    val _id:String,
    val title:String,
    val description:String,
    val price:Int,
    val instructor:String,
    val students:List<String>,
    val __v:Int,
)
