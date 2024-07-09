package com.example.projectfinalandroid.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectfinalandroid.repositories.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(val repository: NoteRepository): ViewModel() {
    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var selectedNote: Note? = null

    val notes = repository.notes

    fun insert (note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }
    fun save(){

        if (!title.value.isNullOrEmpty() && !description.value.isNullOrEmpty()){
            if (selectedNote != null){
                selectedNote?.title = title.value!!
                selectedNote?.description = description.value!!
                update(selectedNote!!)
                selectedNote = null
            }
            val newNote = Note(0,"12/12/2024",title.value!!,description.value!!,234.23, 234.34)
            insert(newNote)
            title.value=""
            description.value=""
        }
    }

    fun selectNote(note: Note){
        selectedNote = note
        title.value = note.title
        description.value = note.description
    }
}