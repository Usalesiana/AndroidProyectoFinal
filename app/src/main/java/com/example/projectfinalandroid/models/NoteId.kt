package com.example.projectfinalandroid.models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NoteId  (
    @PrimaryKey
    @SerializedName("id")
    var id: String
)