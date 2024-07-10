package com.example.projectfinalandroid.repositories

import com.example.projectfinalandroid.api.NoteApiService
import com.example.projectfinalandroid.models.Note
import com.example.projectfinalandroid.room.NoteDao
import kotlinx.coroutines.flow.flow

class NoteRepository(private val noteDao: NoteDao,
                     private val noteApiService: NoteApiService) {
    val notes = noteDao.getAllNotes()

    suspend fun insert (note: Note){
        noteDao.insertNote(note)
    }

    suspend fun update (note: Note){
        noteDao.updateNote(note)
    }
    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
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
}
