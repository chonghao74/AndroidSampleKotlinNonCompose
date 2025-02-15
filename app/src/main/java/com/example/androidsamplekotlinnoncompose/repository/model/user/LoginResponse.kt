package com.example.androidsamplekotlinnoncompose.repository.model.user

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    val code:Int = 999,
    @SerializedName("data")
    val data:Data

)

data class Data(
    val result: String,
    val token: String,
    val user:User
)

data class User(
    val _id:String,
    val email:String,
    val name:String,
    val role:String,
    val date:String,
)
