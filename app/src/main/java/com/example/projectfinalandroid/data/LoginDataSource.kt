package com.example.projectfinalandroid.data

import android.content.Context
import android.content.SharedPreferences
import com.example.projectfinalandroid.api.LoginApiService
import com.example.projectfinalandroid.api.RetrofitInstance
import com.example.projectfinalandroid.models.LoggedInUser
import com.example.projectfinalandroid.models.LoginRequest
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.UUID

class LoginDataSource(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun login(username: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {
        val retrofit = RetrofitInstance.getInstance()
        val loginService = retrofit.create(LoginApiService::class.java)

        loginService.login(LoginRequest(username, password)).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful && response.body() != null) {
                    val jsonElement = JsonParser.parseString(response.body())
                    val userId = jsonElement.asJsonObject.get("user_id").asString

                    saveUserId(userId)

                    val user = LoggedInUser(UUID.randomUUID().toString(), userId)
                    callback(Result.Success(user))
                } else {
                    callback(Result.Error(IOException("Error logging in, response unsuccessful")))
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callback(Result.Error(IOException("Error logging in", t)))
            }
        })
    }

    private fun saveUserId(userId: String) {
        sharedPreferences.edit().putString("user_id", userId).apply()
    }
}
