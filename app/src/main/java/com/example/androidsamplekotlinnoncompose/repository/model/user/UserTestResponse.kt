package com.example.androidsamplekotlinnoncompose.repository.model.user

import com.google.gson.annotations.SerializedName

data class UserTestResponse(
    @SerializedName("userId")
    val userId:Int,
    @SerializedName("id")
    val id:Int,
    @SerializedName("title")
    val title:String,
    @SerializedName("body")
    val body:String,
    val test:String
)
