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

    // LiveData to track loading state for showing/hiding the loading bar
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    // LiveData to hold the PLR and Smooth Pursuit results
    private val _plrResult = MutableLiveData<Plr?>()
    val plrResult: LiveData<Plr?> get() = _plrResult

    private val _smoothPursuitResult = MutableLiveData<SmoothPursuit?>()
    val smoothPursuitResult: LiveData<SmoothPursuit?> get() = _smoothPursuitResult

    // LiveData for any error messages
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Keeps track of the currently selected test
    private var selectedTest: TestType? = null

    /**
     * Called when the user clicks the button to run the PLR test.
     * Sets the selected test type and starts fetching the PLR record.
     */
    fun onPlrButtonClicked() {
        selectedTest = TestType.PLR
        fetchPlr()
    }

    /**
     * Called when the user clicks the button to run the Smooth Pursuit test.
     * Sets the selected test type and starts fetching the Smooth Pursuit record.
     */
    fun onSmoothPursuitButtonClicked() {
        selectedTest = TestType.SMOOTH_PURSUIT
        fetchSmoothPursuit()
    }

    /**
     * Fetches the PLR data from the API.
     * Uses a generous timeout (10 minutes) to allow for long-running requests.
     */
    private fun fetchPlr() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Set a timeout of 10 minutes (600,000 milliseconds)
                val plrData = withTimeout(600_000L) {
                    userDataSource.getPlr()
                }
                _plrResult.value = plrData
            } catch (e: TimeoutCancellationException) {
                _error.value = "PLR request timed out. Please try again."
            } catch (e: Exception) {
                _error.value = "Error fetching PLR data: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Fetches the Smooth Pursuit data from the API.
     * Uses a generous timeout (10 minutes) to allow for long-running requests.
     */
    private fun fetchSmoothPursuit() {
        viewModelScope.launch {
            _isLoading.value = true
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
                _isLoading.value = false
            }
        }
    }

    /**
     * Called when the user presses "navigate to results".
     * Depending on which test was selected, the corresponding record is added to the database.
     */
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


