package com.example.projectfinalandroid.api

import com.example.projectfinalandroid.models.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApiService {
    @POST("/login")
    @Headers("Content-Type: application/json")
    fun login(@Body loginRequest: LoginRequest): Call<String>
}
