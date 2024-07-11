package com.example.projectfinalandroid.models

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectfinalandroid.repositories.NoteRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class NoteViewModel(val repository: NoteRepository, val context: Context) : ViewModel() {
    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var userId = MutableLiveData<String?>()
    var latitude = MutableLiveData<Double?>()
    var longitude= MutableLiveData<Double?>()
    var date = MutableLiveData<String>()
    var selectedNote: Note? = null

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    val notes = repository.notes

    fun insert(note: Note) = viewModelScope.launch {
        repository.insertToApi(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.updateToApi(note)
    }

    fun delete(id: String) = viewModelScope.launch {
        repository.deleteToApi(id)
    }

    fun save() {
        if (!title.value.isNullOrEmpty() && !description.value.isNullOrEmpty()) {
            if (selectedNote != null) {
                selectedNote?.let {
                    it.title = title.value!!
                    it.description = description.value!!
                    it.userId = userId.value ?: ""
                    it.latitude = latitude.value ?: 0.0
                    it.longitude = longitude.value ?: 0.0
                    it.dateCreated = date.value ?: formetDate()
                    update(it)
                }
            } else {
                val newNote = Note(
                    id = "",
                    dateCreated = formetDate(),
                    title = title.value!!,
                    description = description.value!!,
                    latitude = latitude.value ?: 0.0,
                    longitude = longitude.value ?: 0.0,
                    userId = getUserId()?.replace("usuario", "user_") ?: "user_15"
                )
                insert(newNote)
            }
            title.value = ""
            description.value = ""
            latitude.value = null
            longitude.value = null
            userId.value = null
            selectedNote = null
        }
    }

    fun selectNote(note: Note) {
        selectedNote = note
        title.value = note.title
        description.value = note.description
        latitude.value = note.latitude
        longitude.value = note.longitude
        userId.value = note.userId
        date.value = note.dateCreated
    }

    fun clearSelectedNote() {
        selectedNote = null
        title.value = ""
        description.value = ""
        latitude.value = null
        longitude.value = null
        userId.value = null
        date.value = ""
    }

    fun setLocation(lat: Double, lon: Double) {
        latitude.value = lat
        longitude.value = lon
    }

    @SuppressLint("SimpleDateFormat")
    fun formetDate(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy.MM.dd")
        return formatter.format(date)
    }

    private fun getUserId(): String? {
        return sharedPreferences.getString("user_id", null)
    }

    fun getAllContacts() = viewModelScope.launch {
        repository.getAll().collect { result ->
            if (!result) {
                //API errors
            }
        }
    }
}
