package com.example.projectfinalandroid.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectfinalandroid.api.NoteApiService
import com.example.projectfinalandroid.api.RetrofitInstance
import com.example.projectfinalandroid.data.NoteDatabase
import com.example.projectfinalandroid.repositories.NoteRepository

class NoteViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            val database = NoteDatabase.getInstance(context)
            val notesDao = database.noteDao
            val noteApiService = RetrofitInstance.getInstance()
                .create(NoteApiService::class.java)
            val notesRepository = NoteRepository(notesDao, noteApiService)
            return NoteViewModel(notesRepository) as T
        }
        return super.create(modelClass)
    }
}