package com.example.androidsamplekotlinnoncompose.repository.model.user

import java.io.Serializable

data class LocalUser(
    val name:String="Default",
    val age:Int=18
): Serializable
