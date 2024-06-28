package com.example.projectfinalandroid.models

data class Note(
    val dateCreated: String,
    val title: String,
    val body: String,
    val latitude: Double,
    val longitude: Double
)