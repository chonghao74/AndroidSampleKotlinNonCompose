package com.example.androidsamplekotlinnoncompose.webservice.retrofit.service

import com.example.androidsamplekotlinnoncompose.repository.model.course.CourseResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap

interface CourseService {
    @GET("/api/course/search")
    suspend fun getCourse(@Header("Authorization")authorization:String): CourseResponse
    //多個就能用
    @GET("/api/course/search")
    suspend fun getCourse2(@HeaderMap headers: Map<String, String> ): CourseResponse


    //多個 Header 1.多個 Property 2.使用 HashMap
}