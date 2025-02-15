package com.example.androidsamplekotlinnoncompose.repository

import com.example.androidsamplekotlinnoncompose.repository.model.user.LoginRequest
import com.example.androidsamplekotlinnoncompose.repository.model.user.LoginResponse
import com.example.androidsamplekotlinnoncompose.webservice.retrofit.AppClientSingletonManager
import com.example.androidsamplekotlinnoncompose.webservice.retrofit.service.UserService
import java.net.URL

class UserRepository {

     fun getServerUserDataByURL(url:String):String{
        return URL(url)
            .readText()
    }

    suspend fun getServerUserDataByRetrofitOkhttp3(){

    }

    suspend fun getUserDataByGson(loginRequest: LoginRequest):LoginResponse {
        val service = AppClientSingletonManager.getInstanceByGson().create(UserService::class.java)
//        return  service.loginByField(loginRequest.email, loginRequest.password)
//        return  service.loginByPart(email=loginRequest.email, password = loginRequest.password)
        return  service.loginByBody(loginRequest)
    }

    suspend fun getUserDataByMoshi(loginRequest: LoginRequest):LoginResponse {
        val service = AppClientSingletonManager.getInstanceByMoshi().create(UserService::class.java)
        return  service.loginByField(loginRequest.email, loginRequest.password)
//        return  service.loginByPart(email=loginRequest.email, password = loginRequest.password)
//        return  service.loginByBody(loginRequest)
    }

}