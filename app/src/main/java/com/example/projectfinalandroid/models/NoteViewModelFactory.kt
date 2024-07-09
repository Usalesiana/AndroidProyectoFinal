package com.example.projectfinalandroid.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectfinalandroid.data.NoteDatabase
import com.example.projectfinalandroid.repositories.NoteRepository

class NoteViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            val database = NoteDatabase.getInstance(context)
            val dao = database.noteDao
            val notesRepository = NoteRepository(dao)
            return NoteViewModel(notesRepository) as T
        }
        return super.create(modelClass)
    }
}