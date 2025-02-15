package com.example.androidsamplekotlinnoncompose.repository.model.user

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String = "timStu01@gmail.com",
    val password: String = "12345678",
)
