package com.example.neuroshield_app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserList(
    val users: List<User> = listOf(),
)

@Serializable
data class User (
    val id: Int,
    val patient_id: String = "",
    val first_name: String = "",
    val last_name: String = "",
    val team_name: String = "",
    val coach_name: String = "",
    val date_of_hit: String = "",
    val time_of_hit: String = "",
    val created_at: String = ""
)