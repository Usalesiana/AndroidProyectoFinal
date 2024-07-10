package com.example.projectfinalandroid.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectfinalandroid.repositories.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(val repository: NoteRepository) : ViewModel() {
    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var selectedNote: Note? = null

    val notes = repository.notes

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun save() {
        if (!title.value.isNullOrEmpty() && !description.value.isNullOrEmpty()) {
            if (selectedNote != null) {
                selectedNote?.let {
                    it.title = title.value!!
                    it.description = description.value!!
                    update(it)
                }
            } else {
                val newNote = Note(
                    id = 0,  // 0 to auto-generate the ID
                    dateCreated = "12/12/2024",  // replace with actual date
                    title = title.value!!,
                    description = description.value!!,
                    latitude = 234.23,  // replace with actual latitude
                    longitude = 234.34  // replace with actual longitude
                )
                insert(newNote)
            }
            title.value = ""
            description.value = ""
            selectedNote = null
        }
    }

    fun selectNote(note: Note) {
        selectedNote = note
        title.value = note.title
        description.value = note.description
    }
}