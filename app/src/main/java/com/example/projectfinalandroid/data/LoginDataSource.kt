package com.example.projectfinalandroid.data

import com.example.projectfinalandroid.api.NotesApiService
import com.example.projectfinalandroid.api.RetrofitInstance
import com.example.projectfinalandroid.api.LoginRequest
import com.example.projectfinalandroid.data.model.LoggedInUser
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {
        val retrofit = RetrofitInstance.getInstance()
        val loginService = retrofit.create(NotesApiService::class.java)

        loginService.login(LoginRequest(username, password)).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful && response.body() != null) {
                    val jsonElement = JsonParser.parseString(response.body())
                    val userId = jsonElement.asJsonObject.get("user_id").asString

                    val user = LoggedInUser(java.util.UUID.randomUUID().toString(), userId)
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

    fun logout() {
        // TODO: revoke authentication
    }
}
