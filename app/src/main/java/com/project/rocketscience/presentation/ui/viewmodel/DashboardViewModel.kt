package com.project.rocketscience.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.domain.GetCompanyInfoUseCase
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
    val getCompanyInfoUseCase: GetCompanyInfoUseCase
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

    init {
        getCompanyInfo()
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
                    _uiState.value = UiState.Success
                }.onFailure {
                    _uiState.value = UiState.Error(it.message.toString())
                }
            }
        }
    }
}
