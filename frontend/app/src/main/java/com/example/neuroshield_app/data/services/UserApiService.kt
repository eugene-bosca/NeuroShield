package com.example.neuroshield_app.data.services

import android.util.Log
import com.example.neuroshield_app.data.models.CreateUser
import com.example.neuroshield_app.data.models.Plr
import com.example.neuroshield_app.data.models.SmoothPursuit
import com.example.neuroshield_app.data.models.User
import com.example.neuroshield_app.data.models.UserDetails
import com.example.neuroshield_app.data.utils.Api
import com.example.neuroshield_app.data.utils.PiApi
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
import java.util.concurrent.TimeUnit

private const val TAG = "UserApiService"

class UserApiService @Inject constructor() {
    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .build()
    private val gson = Gson()

    suspend fun createUser(userCreation: CreateUser): User =
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

                val resultType = object : TypeToken<User>() {}.type
                val result: User = gson.fromJson(body, resultType)

                return@withContext result
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

    suspend fun getUserDetails(patientId: String): UserDetails = withContext(Dispatchers.IO) {
        val endpoint = "users/$patientId/details/"
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

            val resultType = object : TypeToken<UserDetails>() {}.type
            val result: UserDetails = gson.fromJson(body, resultType)

            Log.d(TAG, "result: $result")

            return@withContext result
        }
    }

    suspend fun getPlr(): Plr = withContext(Dispatchers.IO) {
        val endpoint = "plrs"
        val url = PiApi.url(endpoint)

        Log.d(TAG, "GET: $url")

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            PiApi.handleResponseStatus(response)

            val body = response.body?.string()
                ?: throw HttpException(response.code, "Empty response body")

            val resultType = object : TypeToken<Plr>() {}.type
            val result: Plr = gson.fromJson(body, resultType)

            Log.d(TAG, "PLR from Pi: $result")

            return@withContext result
        }
    }

    suspend fun getSmoothPursuit(): SmoothPursuit = withContext(Dispatchers.IO) {
        val endpoint = "smoothpursuit"
        val url = PiApi.url(endpoint)

        Log.d(TAG, "GET: $url")

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            PiApi.handleResponseStatus(response)

            val body = response.body?.string()
                ?: throw HttpException(response.code, "Empty response body")

            val resultType = object : TypeToken<SmoothPursuit>() {}.type
            val result: SmoothPursuit = gson.fromJson(body, resultType)

            return@withContext result
        }
    }

    suspend fun createPlrRecord(plrRecord: Plr, patientId: String): String =
        withContext(Dispatchers.IO) {
            val endpoint = "users/$patientId/plrs/"
            val url = Api.url(endpoint)

            Log.d(TAG, "POST: $url")

            val requestBody = gson.toJson(plrRecord)
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

                val resultType = object : TypeToken<String>() {}.type
                val result: String = gson.fromJson(body, resultType)

                Log.d(TAG, "Create Plr results: $result")

                return@withContext result
            }
        }

    suspend fun createSpRecord(spRecord: SmoothPursuit, patientId: String): String =
        withContext(Dispatchers.IO) {
            val endpoint = "users/$patientId/smoothpursuits/"
            val url = Api.url(endpoint)

            Log.d(TAG, "POST: $url")

            val requestBody = gson.toJson(spRecord)
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

                val resultType = object : TypeToken<String>() {}.type
                val result: String = gson.fromJson(body, resultType)

                Log.d(TAG, "SP results: $result")

                return@withContext result
            }
        }
}
