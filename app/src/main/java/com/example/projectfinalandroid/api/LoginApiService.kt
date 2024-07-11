package com.example.projectfinalandroid.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class LoginRequest(
    val username: String,
    val password: String
)

interface LoginApiService {
    @POST("/login")
    @Headers("Content-Type: application/json")
    fun login(@Body loginRequest: LoginRequest): Call<String>
}
