package com.project.rocketscience.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.presentation.ui.components.CircularLoading
import com.project.rocketscience.presentation.ui.components.CompanyInfoContent
import com.project.rocketscience.presentation.ui.components.ErrorAlertDialog
import com.project.rocketscience.presentation.ui.state.UiState
import com.project.rocketscience.presentation.ui.viewmodel.DashboardViewModel

@Composable
fun Dashboard(
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {

    val uiState: UiState by dashboardViewModel.uiState.collectAsState()
    val companyInfo by dashboardViewModel.companyInfoFlow.collectAsState()

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        when (uiState) {
            is UiState.Loading -> {
                CircularLoading()
            }

            is UiState.Error -> {
                errorDialogMessage = (uiState as UiState.Error).message
                showErrorDialog = true
            }

            is UiState.Success -> {
            }
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(10.dp)

        ) {
            DashboardLazyColumn(
                companyInfo = companyInfo
            )

            if (showErrorDialog) {
                ErrorAlertDialog(
                    error = errorDialogMessage,
                    onButtonClickAction = { showErrorDialog = false }
                )
            }
        }
    }

}

@Composable
private fun DashboardLazyColumn(
    companyInfo: CompanyInfo?,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            companyInfo?.let { CompanyInfoContent(companyInfo = it) }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}