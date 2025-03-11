package com.example.neuroshield_app.presentation.screens.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neuroshield_app.data.models.User
import com.example.neuroshield_app.data.source.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HistoryViewModel @Inject constructor (
    private val userDataSource: UserDataSource
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val events: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("History", "Fetching Users:")
                // Execute the network call on the IO dispatcher
                val eventItems = withContext(Dispatchers.IO) {
                    userDataSource.getUsers()
                }
                _users.value = eventItems
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