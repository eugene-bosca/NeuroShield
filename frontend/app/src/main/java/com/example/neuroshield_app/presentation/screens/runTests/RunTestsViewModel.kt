package com.example.neuroshield_app.presentation.screens.runTests

import androidx.lifecycle.*
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

    enum class TestType {
        PLR,
        SMOOTH_PURSUIT
    }

    // Global error and navigation loading (if needed)
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // LiveData for the results
    private val _plrResult = MutableLiveData<Plr?>()
    val plrResult: LiveData<Plr?> get() = _plrResult

    private val _smoothPursuitResult = MutableLiveData<SmoothPursuit?>()
    val smoothPursuitResult: LiveData<SmoothPursuit?> get() = _smoothPursuitResult

    // Separate loading states for each test
    private val _plrLoading = MutableLiveData(false)
    val plrLoading: LiveData<Boolean> get() = _plrLoading

    private val _smoothPursuitLoading = MutableLiveData(false)
    val smoothPursuitLoading: LiveData<Boolean> get() = _smoothPursuitLoading

    // Keeps track of the currently selected test if needed (optional)
    private var selectedTest: TestType? = null

    fun onPlrButtonClicked() {
        selectedTest = TestType.PLR
        _plrLoading.value = true
        fetchPlr()
    }

    fun onSmoothPursuitButtonClicked() {
        selectedTest = TestType.SMOOTH_PURSUIT
        _smoothPursuitLoading.value = true
        fetchSmoothPursuit()
    }

    private fun fetchPlr() {
        viewModelScope.launch {
            try {
                // Timeout of 10 minutes
                val plrData = withTimeout(600_000L) {
                    userDataSource.getPlr()
                }
                _plrResult.value = plrData
            } catch (e: TimeoutCancellationException) {
                _error.value = "PLR request timed out. Please try again."
            } catch (e: Exception) {
                _error.value = "Error fetching PLR data: ${e.localizedMessage}"
            } finally {
                _plrLoading.value = false
            }
        }
    }

    private fun fetchSmoothPursuit() {
        viewModelScope.launch {
            try {
                val spData = withTimeout(600_000L) {
                    userDataSource.getSmoothPursuit()
                }
                _smoothPursuitResult.value = spData
            } catch (e: TimeoutCancellationException) {
                _error.value = "Smooth Pursuit request timed out. Please try again."
            } catch (e: Exception) {
                _error.value = "Error fetching Smooth Pursuit data: ${e.localizedMessage}"
            } finally {
                _smoothPursuitLoading.value = false
            }
        }
    }

    fun onNavigateToResults(patientId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (selectedTest) {
                    TestType.PLR -> {
                        _plrResult.value?.let { plr ->
                            userDataSource.createPlrRecord(plr, patientId)
                        } ?: run {
                            _error.value = "PLR data not available."
                        }
                    }
                    TestType.SMOOTH_PURSUIT -> {
                        _smoothPursuitResult.value?.let { sp ->
                            userDataSource.createSpRecord(sp, patientId)
                        } ?: run {
                            _error.value = "Smooth Pursuit data not available."
                        }
                    }
                    else -> {
                        _error.value = "No test selected."
                    }
                }
            } catch (e: Exception) {
                _error.value = "Error saving record: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
