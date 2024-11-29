package com.example.hackcbs.network


import com.example.hackcbs.data.ApiResponse
import com.example.hackcbs.data.UserData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("your/api/endpoint") // Replace with your actual API endpoint
    fun sendUserData(@Body userData: UserData): Call<ApiResponse>
}

object ApiClient {
    private const val BASE_URL = "https://your.api.base.url" // Replace with your actual base URL

    val apiService: ApiService by lazy {
        Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}