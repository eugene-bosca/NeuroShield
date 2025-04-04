package com.example.neuroshield_app.presentation.screens.runTests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neuroshield_app.data.models.Plr
import com.example.neuroshield_app.data.models.SmoothPursuit
import com.example.neuroshield_app.data.source.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class RunTestsViewModel @Inject constructor(
    private val userDataSource: UserDataSource
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _plrResult = MutableLiveData<Plr?>()
    val plrResult: LiveData<Plr?> get() = _plrResult

    private val _smoothPursuitResult = MutableLiveData<SmoothPursuit?>()
    val smoothPursuitResult: LiveData<SmoothPursuit?> get() = _smoothPursuitResult

    private val _plrLoading = MutableLiveData(false)
    val plrLoading: LiveData<Boolean> get() = _plrLoading

    private val _smoothPursuitLoading = MutableLiveData(false)
    val smoothPursuitLoading: LiveData<Boolean> get() = _smoothPursuitLoading

    fun onPlrButtonClicked(patientId: String) {
        _plrLoading.value = true
        fetchPlr(patientId)
    }

    fun onSmoothPursuitButtonClicked(patientId: String) {
        _smoothPursuitLoading.value = true
        fetchSmoothPursuit(patientId)
    }

    private fun fetchPlr(patientId: String) {
        viewModelScope.launch {
            try {
                val plrData = withTimeout(600_000L) {
                    userDataSource.getPlr() // or whatever your data fetch call is
                }
                Log.d("RunTestsViewModel", "Fetched PLR data: $plrData")
                _plrResult.value = plrData

                // Immediately save PLR to the DB
                userDataSource.createPlrRecord(plrData, patientId)
                Log.d("RunTestsViewModel", "Saved PLR data to DB.")

            } catch (e: TimeoutCancellationException) {
                _error.value = "PLR request timed out. Please try again."
            } catch (e: Exception) {
                _error.value = "Error fetching PLR data: ${e.localizedMessage}"
            } finally {
                _plrLoading.value = false
            }
        }
    }

    private fun fetchSmoothPursuit(patientId: String) {
        viewModelScope.launch {
            try {
                val spData = withTimeout(600_000L) {
                    userDataSource.getSmoothPursuit()
                }
                Log.d("RunTestsViewModel", "Fetched SP data: $spData")
                _smoothPursuitResult.value = spData

                // Immediately save Smooth Pursuit to the DB
                userDataSource.createSpRecord(spData, patientId)
                Log.d("RunTestsViewModel", "Saved Smooth Pursuit data to DB.")

            } catch (e: TimeoutCancellationException) {
                _error.value = "Smooth Pursuit request timed out. Please try again."
            } catch (e: Exception) {
                _error.value = "Error fetching Smooth Pursuit data: ${e.localizedMessage}"
            } finally {
                _smoothPursuitLoading.value = false
            }
        }
    }

    fun onNavigateToResults() {
        // No need to save data here anymore. They have already been saved
        // in fetchPlr(...) and fetchSmoothPursuit(...).
        // Just handle navigation or any final checks before going to the next screen.
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // e.g., _navigationEvent.value = SomeNavigationCommand(...)
                // or whatever logic you need before moving on
            } catch (e: Exception) {
                _error.value = "Error navigating: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
