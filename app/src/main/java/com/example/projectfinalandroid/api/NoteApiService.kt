package com.example.projectfinalandroid.api

import com.example.projectfinalandroid.models.Note
import com.example.projectfinalandroid.models.NoteId
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface NoteApiService {
    @GET("/notes")
    suspend fun getNotes(@Query("user_id") userId: String): Response<ArrayList<Note>>

    @POST("/notes")
    fun postNote(@Body note: Note): Call<String>

    @PUT("/notes")
    fun putNote(@Body note: Note): Call<String>

    @HTTP(method = "DELETE", path = "notes", hasBody = true)
    suspend fun deleteNote(@Body note: NoteId): Response<String>
}