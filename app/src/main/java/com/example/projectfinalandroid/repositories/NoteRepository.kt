package com.example.projectfinalandroid.repositories

import com.example.projectfinalandroid.api.NoteApiService
import com.example.projectfinalandroid.models.Note
import com.example.projectfinalandroid.models.NoteId
import com.example.projectfinalandroid.room.NoteDao
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse

class NoteRepository(private val noteDao: NoteDao,
                     private val noteApiService: NoteApiService) {
    val notes = noteDao.getAllNotes()

    fun getAll() = flow {
        val result = noteApiService.getNotes()
        if (result.isSuccessful() && result.body() != null){
            noteDao.insertAll(result.body()!!)
            emit(true)
        } else {
        emit(false)
        }
    }

    suspend fun insertToApi(note: Note){
        val call = noteApiService.postNote(note)
        val response = call.awaitResponse()
        if (response.isSuccessful){
            getAll().collect(){
            }
        }
    }

    suspend fun updateToApi(note: Note){
        val call = noteApiService.putNote(note)
        val response = call.awaitResponse()
        if (response.isSuccessful){
            getAll().collect(){}
        }
    }
    suspend fun deleteToApi(noteId: String) {
        val response = noteApiService.deleteNote(NoteId(noteId))
        if (response.isSuccessful) {
            getAll().collect {}
        } else {
            error("No se pudo eliminar la nota")
        }
    }
}
