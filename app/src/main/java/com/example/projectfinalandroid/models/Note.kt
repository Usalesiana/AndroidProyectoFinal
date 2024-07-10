package com.example.projectfinalandroid.models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("notes")
data class Note(
    @PrimaryKey
    @SerializedName("id")
    var id: String,
    @SerializedName("fecha")
    var dateCreated: String,
    @SerializedName("titulo")
    var title: String,
    @SerializedName("body")
    var description: String,
    @SerializedName("latitud")
    var latitude: Double,
    @SerializedName("longitud")
    var longitude: Double,
    @SerializedName("user_id")
    var userId: String
)