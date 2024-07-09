package com.example.projectfinalandroid.repositories

import com.example.projectfinalandroid.api.NotesApiService
import com.example.projectfinalandroid.api.LoginRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class NoteRepository(private val notesApiService: NotesApiService) {

    fun login(username: String, password: String): Flow<Response<String>> = flow {
        val loginRequest = LoginRequest(username, password)
        val response = notesApiService.login(loginRequest).execute()
        emit(response)
    }
}
