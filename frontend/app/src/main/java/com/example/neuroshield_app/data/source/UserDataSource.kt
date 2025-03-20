package com.example.neuroshield_app.data.source

import android.util.Log
import com.example.neuroshield_app.data.models.CreateUser
import com.example.neuroshield_app.data.models.Plr
import com.example.neuroshield_app.data.models.SmoothPursuit
import com.example.neuroshield_app.data.models.User
import com.example.neuroshield_app.data.models.UserDetails
import com.example.neuroshield_app.data.services.UserApiService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

    suspend fun getUserDetails(patientId: String): UserDetails {
        Log.d(TAG, "getUser for $patientId")
        val userResponse = userApi.getUserDetails(patientId)
        return userResponse
    }

    suspend fun createUsers(user: CreateUser): User {

        Log.d(TAG, "create User for $user")

        val userResponse = userApi.createUser(user)
        return userResponse
    }

    suspend fun getPlr(): Plr {

        Log.d(TAG, "Running Plr Test")

        val piResponse = userApi.getPlr()
        return piResponse
    }

    suspend fun getSmoothPursuit(): SmoothPursuit {

        Log.d(TAG, "Running Smooth Pursuit Test")

        val piResponse = userApi.getSmoothPursuit()
        return piResponse
    }

    suspend fun createPlrRecord(plrRecord: Plr, patientId: String): String {

        Log.d(TAG, "create Plr Record for $patientId")

        val userResponse = userApi.createPlrRecord(plrRecord, patientId)
        return userResponse
    }

    suspend fun createSpRecord(spRecord: SmoothPursuit, patientId: String): String {

        Log.d(TAG, "create Smooth Pursuit Record for $patientId")

        val userResponse = userApi.createSpRecord(spRecord, patientId)
        return userResponse
    }
}