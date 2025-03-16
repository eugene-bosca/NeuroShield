package com.example.neuroshield_app.data.services

import android.util.Log
import com.example.neuroshield_app.data.models.CreateUser
import com.example.neuroshield_app.data.models.User
import com.example.neuroshield_app.data.utils.Api
import com.example.neuroshield_app.data.utils.HttpException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

private const val TAG = "UserApiService"

class UserApiService @Inject constructor() {
    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun createUser(userCreation: CreateUser): String =
        withContext(Dispatchers.IO) {
            val endpoint = "users/"
            val url = Api.url(endpoint)

            Log.d(TAG, "POST: $url")

            val requestBody = gson.toJson(userCreation)
                .toRequestBody("application/json".toMediaType())

            Log.d(TAG, "POST: $requestBody")

            val request = Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                Api.handleResponseStatus(response)

                val body = response.body?.string()
                    ?: throw HttpException(response.code, "Empty response body")

                return@withContext body
            }
        }

    suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        val endpoint = "users/"
        val url = Api.url(endpoint)

        Log.d(TAG, "GET: $url")

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            Api.handleResponseStatus(response)

            val body = response.body?.string()
                ?: throw HttpException(response.code, "Empty response body")

            val resultType = object : TypeToken<List<User>>() {}.type
            val result: List<User> = gson.fromJson(body, resultType)

            return@withContext result
        }
    }

    suspend fun getUser(patientId: String): User = withContext(Dispatchers.IO) {
        val endpoint = "users/$patientId/"
        val url = Api.url(endpoint)

        Log.d(TAG, "GET: $url")

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            Api.handleResponseStatus(response)

            val body = response.body?.string()
                ?: throw HttpException(response.code, "Empty response body")

            val resultType = object : TypeToken<User>() {}.type
            val result: User = gson.fromJson(body, resultType)

            return@withContext result
        }
    }
}