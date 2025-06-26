package com.project.rocketscience.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
import com.project.rocketscience.domain.GetCompanyInfoUseCase
import com.project.rocketscience.domain.GetFilteredLaunchesUseCase
import com.project.rocketscience.domain.GetLaunchesUseCase
import com.project.rocketscience.presentation.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [DashboardViewModel] is responsible for managing UI state and business logic
 * related to fetching SpaceX company information. It uses a use cases
 * to retrieve the data and exposes [StateFlow]s for the UI to observe.
 *
 * This ViewModel is annotated with `@HiltViewModel` and is provided via Hilt dependency injection.
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCompanyInfoUseCase: GetCompanyInfoUseCase,
    private val getLaunchesUseCase: GetLaunchesUseCase,
    private val getFilteredLaunchesUseCase: GetFilteredLaunchesUseCase
) : ViewModel() {
    // Mutable state for internal use only
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)

    /**
     * Publicly exposed [StateFlow] for observing the overall UI state.
     * UI components should collect this flow to react to loading, success, or error conditions.
     */
    val uiState: StateFlow<UiState> = _uiState

    // Mutable state for internal use only
    private val _companyInfoFlow = MutableStateFlow<CompanyInfo?>(null)

    /**
     * Publicly exposed [StateFlow] for observing the SpaceX company information.
     * UI components should collect this flow to receive updates.
     */
    val companyInfoFlow: StateFlow<CompanyInfo?> = _companyInfoFlow

    // Mutable state for internal use only
    private val _launchFlow = MutableStateFlow<List<Launch>>(emptyList())

    /**
     * Publicly exposed [StateFlow] for observing the list of launches to be displayed.
     * UI components should collect this flow to receive updates.
     */
    val launchFlow: StateFlow<List<Launch>> = _launchFlow

    init {
        getCompanyInfo()
        getLaunches()
    }

    /**
     * Initiates collection of company information using the [GetCompanyInfoUseCase].
     * Updates [_companyInfoFlow] and [_uiState] based on the result.
     */
    private fun getCompanyInfo() {
        viewModelScope.launch {
            getCompanyInfoUseCase().collect { result ->
                result.onSuccess { companyInfo ->
                    _companyInfoFlow.value = companyInfo
                }.onFailure {
                    _uiState.value = UiState.Error(it.message.toString())
                }
            }
        }
    }

    /**
     * Fetches all rocket launches using [getLaunchesUseCase] and updates the UI state accordingly.
     *
     * This method is executed within the [viewModelScope] to collect the result from the use case.
     * On success, the list of launches is stored in [_launchFlow] and the UI is updated to [UiState.Success].
     * On failure, an error message is stored in [UiState.Error] for appropriate UI feedback.
     */
    private fun getLaunches() {
        viewModelScope.launch {
            getLaunchesUseCase().collect { result ->
                result.onSuccess { launches ->
                    _launchFlow.value = launches
                    _uiState.value = UiState.Success
                }.onFailure {
                    _uiState.value = UiState.Error(it.message.toString())
                }
            }
        }
    }

    /**
     * Retrieves a filtered and optionally sorted list of rocket launches.
     *
     * This function uses [getFilteredLaunchesUseCase] to fetch launches based on the provided filter criteria,
     * then updates the UI state and the internal [launchFlow] with the result.
     *
     * @param filterYearsList A list of years to filter the launches by. If empty, all years are considered.
     * @param organizeDescending If true, the resulting launches are sorted by year in descending order.
     */
    fun getFilteredLaunches(filterYearsList: List<String>, organizeDescending: Boolean) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getFilteredLaunchesUseCase(
                filterYearsList = filterYearsList,
                organizeDescending = organizeDescending
            ).collect { result ->
                result.onSuccess { launches ->
                    _launchFlow.value = launches
                    _uiState.value = UiState.Success
                }.onFailure {
                    _uiState.value = UiState.Error(it.message.toString())
                }
            }
        }
    }
}
