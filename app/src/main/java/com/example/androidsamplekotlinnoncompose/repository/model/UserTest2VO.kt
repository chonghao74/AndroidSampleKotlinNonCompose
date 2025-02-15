package com.example.androidsamplekotlinnoncompose.repository.model

import com.google.gson.annotations.SerializedName

data class UserTest2VO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)
