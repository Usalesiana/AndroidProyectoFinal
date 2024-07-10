package com.example.projectfinalandroid.api

import com.example.projectfinalandroid.models.Note
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoteApiService {
    @GET("/notes")
    suspend fun getNotes(): Response<ArrayList<Note>>

    @POST("/notes")
    fun postNote(@Body note: Note): Call<String>

}