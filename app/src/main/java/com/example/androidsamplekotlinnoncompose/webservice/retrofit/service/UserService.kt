package com.example.androidsamplekotlinnoncompose.webservice.retrofit.service

import com.example.androidsamplekotlinnoncompose.repository.model.user.LoginRequest
import com.example.androidsamplekotlinnoncompose.repository.model.user.LoginResponse
import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserService {
    @FormUrlEncoded
    @POST("/api/user/login")
    @Headers( "Content-Type: application/json")
    suspend fun loginByField(
        @Field("email") email: String = "timStu01@gmail.com",
        @Field("password") password: String = "12345678",
    ): LoginResponse//Kotlin 可以省略 Call<T>

//    @Multipart
//    @POST("/api/user/login")
//    suspend fun loginByPart(
//        @Part("email") email: String = "timStu01@gmail.com",
//        @Part("password") password: String = "12345678",
//    ): LoginResponse

    @Json //可寫可不寫
    @POST("/api/user/login")
    @Headers( "Content-Type: application/json")
    suspend fun loginByBody(
        @Body loginRequest: LoginRequest
    ): LoginResponse

}