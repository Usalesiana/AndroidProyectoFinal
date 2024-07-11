package com.example.projectfinalandroid.models

import com.example.projectfinalandroid.models.LoggedInUserView

data class LoginResult (
     val success: LoggedInUserView? = null,
     val error:Int? = null
)