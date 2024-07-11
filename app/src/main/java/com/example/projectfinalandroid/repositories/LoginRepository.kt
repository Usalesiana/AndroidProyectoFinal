package com.example.projectfinalandroid.repositories

import com.example.projectfinalandroid.data.LoginDataSource
import com.example.projectfinalandroid.data.Result
import com.example.projectfinalandroid.models.LoggedInUser

class LoginRepository(val dataSource: LoginDataSource) {

    var user: LoggedInUser? = null
        private set

    init {
        user = null
    }

    fun login(username: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {
        dataSource.login(username, password) { result ->
            if (result is Result.Success) {
                setLoggedInUser(result.data)
            }
            callback(result)
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}
