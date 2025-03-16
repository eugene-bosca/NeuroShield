package com.example.neuroshield_app.presentation.screens.userInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neuroshield_app.data.models.CreateUser
import com.example.neuroshield_app.data.source.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userDataSource: UserDataSource
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _teamName = MutableStateFlow("")
    val teamName: StateFlow<String> = _teamName

    private val _coachName = MutableStateFlow("")
    val coachName: StateFlow<String> = _coachName

    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String> = _selectedDate

    private val _selectedTime = MutableStateFlow("")
    val selectedTime: StateFlow<String> = _selectedTime

    private val _isRequestSuccessful = MutableStateFlow<Boolean?>(null)
    val isRequestSuccessful: StateFlow<Boolean?> = _isRequestSuccessful

    // Error messages
    private val _firstNameError = MutableStateFlow<String?>(null)
    val firstNameError: StateFlow<String?> = _firstNameError

    private val _lastNameError = MutableStateFlow<String?>(null)
    val lastNameError: StateFlow<String?> = _lastNameError

    private val _teamNameError = MutableStateFlow<String?>(null)
    val teamNameError: StateFlow<String?> = _teamNameError

    private val _coachNameError = MutableStateFlow<String?>(null)
    val coachNameError: StateFlow<String?> = _coachNameError

    private val _dateError = MutableStateFlow<String?>(null)
    val dateError: StateFlow<String?> = _dateError

    private val _timeError = MutableStateFlow<String?>(null)
    val timeError: StateFlow<String?> = _timeError

    // Methods to update field values and clear errors when typing
    fun onFirstNameChange(newValue: String) {
        _firstName.value = newValue
        _firstNameError.value = null
    }

    fun onLastNameChange(newValue: String) {
        _lastName.value = newValue
        _lastNameError.value = null
    }

    fun onTeamNameChange(newValue: String) {
        _teamName.value = newValue
        _teamNameError.value = null
    }

    fun onCoachNameChange(newValue: String) {
        _coachName.value = newValue
        _coachNameError.value = null
    }

    fun onDateSelected(millis: Long) {
        _selectedDate.value = convertMillisToISODate(millis)
        _dateError.value = null
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        _selectedTime.value = String.format("%02d:%02d:00", hour, minute)
        _timeError.value = null
    }

    private fun convertMillisToISODate(millis: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    fun onContinueClicked() {

        if (_isLoading.value) return // Prevent duplicate clicks

        _isLoading.value = true // Start loading

        // Validate fields
        var isValid = true

        if (firstName.value.isBlank()) {
            _firstNameError.value = "First name is required"
            isValid = false
        }
        if (lastName.value.isBlank()) {
            _lastNameError.value = "Last name is required"
            isValid = false
        }
        if (teamName.value.isBlank()) {
            _teamNameError.value = "Team name is required"
            isValid = false
        }
        if (coachName.value.isBlank()) {
            _coachNameError.value = "Coach name is required"
            isValid = false
        }
        if (selectedDate.value.isBlank()) {
            _dateError.value = "Please select a date"
            isValid = false
        }
        if (selectedTime.value.isBlank()) {
            _timeError.value = "Please select a time"
            isValid = false
        }

        if (!isValid) {
            _isLoading.value = false
            return
        }

        viewModelScope.launch {
            val user = CreateUser(
                first_name = firstName.value,
                last_name = lastName.value,
                team_name = teamName.value,
                coach_name = coachName.value,
                date_of_hit = selectedDate.value,
                time_of_hit = selectedTime.value,
                created_at = getCurrentTimestampISO()
            )

            try {
                val response = userDataSource.createUsers(user)
                Log.d("UserInfoViewModel", "User created: $response")
                _isRequestSuccessful.value = true
            } catch (e: Exception) {
                Log.e("UserInfoViewModel", "Error creating user: ${e.localizedMessage}")
                _isRequestSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getCurrentTimestampISO(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        return formatter.format(Date())
    }
}
