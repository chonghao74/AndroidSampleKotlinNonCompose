package com.example.androidsamplekotlinnoncompose.webservice.retrofit

import com.example.androidsamplekotlinnoncompose.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class AppClientSingletonManager {

    companion object {
        private var retrofitByGson: Retrofit? = null
        private var retrofitByMoshi: Retrofit? = null

        //        private val okHttpClient = OkHttpClient()
        private const val BASE_URL = BuildConfig.API_Host_URL;
//        private  const val BASE_URL = "http://192.168.0.72:1001"

        private var okHttpClientDefault = OkHttpClient()
        private val okHttpClientSetting = OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
//                                                        .addNetworkInterceptor(TestInterceptor())
            .addInterceptor(TestInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) {
//                                                                HttpLoggingInterceptor.Level.BASIC // req + res
//                                                                HttpLoggingInterceptor.Level.HEADERS // req + res + headers
                        HttpLoggingInterceptor.Level.BODY // req + res + headers + body
                    } else {
                        HttpLoggingInterceptor.Level.NONE //不打印
                    }
                )
            )
            .build()

        fun getInstanceByGson(): Retrofit {
            if (retrofitByGson == null) {
                retrofitByGson = Retrofit.Builder()
                    .client(okHttpClientSetting)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofitByGson as Retrofit
        }

        fun getInstanceByMoshi(): Retrofit {
            if (retrofitByMoshi == null) {
                retrofitByMoshi = Retrofit.Builder()
                    .client(OkHttpClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
            }
            return retrofitByMoshi as Retrofit
        }


    }

}

class TestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //  重寫請求
        val request = chain.request()
        val newRequest = request.newBuilder()
//            .addHeader("Content-Type", "application/json")
//            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()
        return chain.proceed(newRequest)

        //  重寫響應
//        val response = chain.proceed(newRequest);
//        val newResponse = response.newBuilder()
//            .header("Cache-Control", "max-age=60")
//            .build()
//
//        return newResponse
    }
}