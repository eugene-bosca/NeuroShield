package com.example.neuroshield_app.presentation.screens.results

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neuroshield_app.data.models.User
import com.example.neuroshield_app.data.models.UserDetails
import com.example.neuroshield_app.data.source.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor (
    private val userDataSource: UserDataSource
) : ViewModel() {
    private val _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails: StateFlow<UserDetails?> = _userDetails

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchUser(patientId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("History", "Fetching User: $patientId")
                // Execute the network call on the IO dispatcher
                val userItems = withContext(Dispatchers.IO) {
                    userDataSource.getUserDetails(patientId)
                }
                _userDetails.value = userItems
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "An unknown error occurred"
                Log.e("HistoryViewModel", "Error fetching events", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}