package com.example.neuroshield_app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserList(
    val users: List<User> = listOf(),
)

@Serializable
data class User (
    val patient_id: String = "",
    val first_name: String = "",
    val last_name: String = "",
    val team_name: String = "",
    val coach_name: String = "",
    val date_of_hit: String = "",
    val time_of_hit: String = "",
    val created_at: String = ""
)

@Serializable
data class CreateUser (
    val first_name: String = "",
    val last_name: String = "",
    val team_name: String = "",
    val coach_name: String = "",
    val date_of_hit: String = "",
    val time_of_hit: String = "",
    val created_at: String = ""
)

@Serializable
data class Plr(
    val id: Int = 0,
    val max_pupil_diam: Double = 0.0,
    val min_pupil_diam: Double = 0.0,
    val percent_contstriction: Double = 0.0,
    val peak_constriction_velocity: Double = 0.0,
    val average_constriction_velocity: Double = 0.0,
    val peak_dilation_velocity: Double = 0.0,
    val average_dilation_velocity: Double = 0.0,
    val time_to_redilation: Double = 0.0,
    val tested_at: String = "",
    val patient_id: String = ""
)

@Serializable
data class SmoothPursuit(
    val id: Int = 0,
    val phase_lag: Double = 0.0,
    val mean_squared_error: Double = 0.0,
    val pearson_coefficient: Double = 0.0,
    val tested_at: String = "",
    val patient_id: String = ""
)

@Serializable
data class UserDetails(
    val user: User? = null,
    val plr: Plr? = null,
    val smooth_pursuit: SmoothPursuit? = null
)