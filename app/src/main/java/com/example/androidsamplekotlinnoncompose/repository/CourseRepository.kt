package com.example.androidsamplekotlinnoncompose.repository

import com.example.androidsamplekotlinnoncompose.repository.model.course.CourseResponse
import com.example.androidsamplekotlinnoncompose.repository.model.user.LoginRequest
import com.example.androidsamplekotlinnoncompose.repository.model.user.LoginResponse
import com.example.androidsamplekotlinnoncompose.webservice.retrofit.AppClientSingletonManager
import com.example.androidsamplekotlinnoncompose.webservice.retrofit.service.CourseService

class CourseRepository {

    suspend fun getCourseByGson(token: String): CourseResponse {
        val service = AppClientSingletonManager.getInstanceByGson().create(CourseService::class.java)
        return  service.getCourse(token)
    }

    suspend fun getCourseByMoshi(token: String): CourseResponse {
        val service = AppClientSingletonManager.getInstanceByMoshi().create(CourseService::class.java)
        return  service.getCourse(token)
    }
}