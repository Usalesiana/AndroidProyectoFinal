package com.example.projectfinalandroid.repositories

import com.example.projectfinalandroid.api.NoteApiService
import com.example.projectfinalandroid.models.Note
import com.example.projectfinalandroid.room.NoteDao
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse

class NoteRepository(private val noteDao: NoteDao,
                     private val noteApiService: NoteApiService) {
    val notes = noteDao.getAllNotes()

    suspend fun insert (note: Note){
        noteDao.insertNote(note)
    }

    suspend fun update (note: Note){
        noteApiService.putNote(note)
    }
    suspend fun delete(id: String) {
        noteApiService.deleteNote(id)
    }

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
        } else {
            //no hace nada
        }
    }

    suspend fun updateToApi(note: Note){
        val call = noteApiService.putNote(note)
        val response = call.awaitResponse()
        if (response.isSuccessful){
            getAll().collect(){}
        }
    }
    suspend fun deleteToApi(noteId: String){
        val call = noteApiService.deleteNote(noteId)
        val response = call.awaitResponse()
        if (response.isSuccessful){
            getAll().collect(){}
        } else {
            error("no se elimino")
        }
    }
}
