package com.example.projectfinalandroid.repositories

<<<<<<< HEAD
class NoteRepository {
}
=======
import com.example.projectfinalandroid.models.Note
import com.example.projectfinalandroid.room.NoteDao

class NoteRepository(val noteDao: NoteDao) {
    val notes = noteDao.getAllNotes()

    suspend fun insert (note: Note){
        noteDao.insertNote(note)
    }

    suspend fun update (note: Note){
        noteDao.updateNote(note)
    }
}
>>>>>>> 74dc947 (add room for notes and configurations for insert notes)
