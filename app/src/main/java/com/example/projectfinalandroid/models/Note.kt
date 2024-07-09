package com.example.projectfinalandroid.models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var dateCreated: String,
    var title: String,
    var description: String,
    var latitude: Double,
    var longitude: Double
)