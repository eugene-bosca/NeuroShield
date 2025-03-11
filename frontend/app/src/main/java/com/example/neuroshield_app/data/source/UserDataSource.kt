package com.example.neuroshield_app.data.source

import android.util.Log
import com.example.neuroshield_app.data.models.User
import com.example.neuroshield_app.data.services.UserApiService
import javax.inject.Singleton
import javax.inject.Inject

private const val TAG = "UserDataSource"

@Singleton
class UserDataSource @Inject constructor(
    private val userApi: UserApiService
) {
    suspend fun getUsers(): List<User> {

        Log.d(TAG, "getUsers for all users")

        val userResponse = userApi.getUsers()
        return userResponse
    }
}