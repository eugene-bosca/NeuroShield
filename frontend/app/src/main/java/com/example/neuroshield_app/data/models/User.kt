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
    val max_pupil_diam_l: Double = 0.0,
    val min_pupil_diam_l: Double = 0.0,
    val percent_contstriction_l: Double = 0.0,
    val peak_constriction_velocity_l: Double = 0.0,
    val average_constriction_velocity_l: Double = 0.0,
    val peak_dilation_velocity_l: Double = 0.0,
    val average_dilation_velocity_l: Double = 0.0,
    val time_to_redilation_l: Double = 0.0,
    val max_pupil_diam_r: Double = 0.0,
    val min_pupil_diam_r: Double = 0.0,
    val percent_contstriction_r: Double = 0.0,
    val peak_constriction_velocity_r: Double = 0.0,
    val average_constriction_velocity_r: Double = 0.0,
    val peak_dilation_velocity_r: Double = 0.0,
    val average_dilation_velocity_r: Double = 0.0,
    val time_to_redilation_r: Double = 0.0,
)

@Serializable
data class SmoothPursuit(
    val phase_lag_l_180: Double = 0.0,
    val mean_squared_error_l_180: Double = 0.0,
    val pearson_coefficient_l_180: Double = 0.0,
    val phase_lag_r_180: Double = 0.0,
    val mean_squared_error_r_180: Double = 0.0,
    val pearson_coefficient_r_180: Double = 0.0,
    val phase_lag_l_360: Double = 0.0,
    val mean_squared_error_l_360: Double = 0.0,
    val pearson_coefficient_l_360: Double = 0.0,
    val phase_lag_r_360: Double = 0.0,
    val mean_squared_error_r_360: Double = 0.0,
    val pearson_coefficient_r_360: Double = 0.0
)

@Serializable
data class UserDetails(
    val user: User? = null,
    val plr: Plr? = null,
    val smooth_pursuit: SmoothPursuit? = null
)