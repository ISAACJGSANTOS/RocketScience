package com.project.rocketscience.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.project.rocketscience.R
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
import com.project.rocketscience.presentation.ui.components.CircularLoading
import com.project.rocketscience.presentation.ui.components.ErrorAlertDialog
import com.project.rocketscience.presentation.ui.components.MoreInfoDialog
import com.project.rocketscience.presentation.ui.components.TopAppBar
import com.project.rocketscience.presentation.ui.components.YearFilterAlertDialog
import com.project.rocketscience.presentation.ui.state.UiState
import com.project.rocketscience.presentation.ui.viewmodel.DashboardViewModel

/**
 * Main screen composable that displays company info and a list of launches.
 *
 * This composable sets up the scaffold layout for the dashboard screen. It includes a top app bar
 * with a filter icon, displays the company info and launches, and handles different UI states
 * such as loading, error, and success. Users can interact with the list of launches to see
 * more info, and apply filters by launch year.
 *
 * @param dashboardViewModel The ViewModel that provides the data for the dashboard screen.
 * Injected automatically by Hilt if not passed explicitly.
 * @param launchInfoNavAction Callback function invoked when a launch info item (Wikipedia or
 * YouTube link) is selected. Receives the URL as a parameter to be used in navigation.
 */
@Composable
fun Dashboard(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    launchInfoNavAction: (String) -> Unit
) {
    val uiState: UiState by dashboardViewModel.uiState.collectAsState()
    val companyInfo by dashboardViewModel.companyInfoFlow.collectAsState()
    val launchesList by dashboardViewModel.launchFlow.collectAsState()

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf("") }

    var showMoreInfoDialog by remember { mutableStateOf(false) }
    var selectedLaunchItem by remember { mutableStateOf<Launch?>(null) }

    var showYearFilterDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "RocketScienceApp",
                showFilterIcon = true,
                onFilterIconClick = { showYearFilterDialog = true }
            )
        }
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
            DashboardContent(
                companyInfo = companyInfo,
                launchesList = launchesList,
                onLaunchItemClick = { launch ->
                    selectedLaunchItem = launch
                    showMoreInfoDialog = true
                }
            )

            if (showErrorDialog) {
                ErrorAlertDialog(
                    error = errorDialogMessage,
                    onButtonClickAction = { showErrorDialog = false }
                )
            }

            if (showMoreInfoDialog) {
                selectedLaunchItem?.let {
                    MoreInfoDialog(
                        onWikipediaButtonClickAction = { launchInfoNavAction(it.links.wikipediaLink) },
                        onYoutubeButtonClickAction = { launchInfoNavAction(it.links.videoLink) },
                        onDismissClickAction = { showMoreInfoDialog = false }
                    )
                }
            }

            if (showYearFilterDialog) {
                YearFilterAlertDialog(
                    onApplyFilterButtonClick = { filterYearsList, organizeDescending ->
                        dashboardViewModel.getFilteredLaunches(
                            filterYearsList = filterYearsList,
                            organizeDescending = organizeDescending
                        )
                    },
                    onDismissClickAction = { showYearFilterDialog = false }
                )
            }
        }
    }
}

@Composable
private fun DashboardContent(
    companyInfo: CompanyInfo?,
    launchesList: List<Launch>?,
    onLaunchItemClick: (Launch) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            companyInfo?.let { CompanyInfoContent(companyInfo = it) }

            Spacer(modifier = Modifier.height(16.dp))
            LaunchesHeaderTitle()
            Spacer(modifier = Modifier.height(16.dp))
        }
        launchesList?.let {
            items(it) { launch ->
                LaunchItemRow(
                    launch = launch,
                    onItemClickAction = {
                        onLaunchItemClick(launch)
                    }
                )
            }
        }
    }
}

@Composable
private fun CompanyInfoContent(companyInfo: CompanyInfo) {
    Text(
        text = stringResource(id = R.string.dashboard_company_header_text),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = companyInfo.getCompanyDescription(),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun LaunchesHeaderTitle() {
    Text(
        text = stringResource(id = R.string.dashboard_launches_header_text),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
}

@Composable
private fun LaunchItemRow(launch: Launch, onItemClickAction: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onItemClickAction)
            .testTag("LaunchItemRow_${launch.missionName}"),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp),
            contentAlignment = Alignment.TopCenter

        ) {
            AsyncImage(
                model = launch.links.missionPatchImage,
                contentDescription = launch.missionName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(48.dp),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                fallback = painterResource(id = R.drawable.ic_launcher_foreground)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            LaunchItemDescription(launch)
        }

        Icon(
            imageVector = if (launch.launchSuccess) Icons.Default.Check else Icons.Default.Close,
            contentDescription = "launch success: ${launch.launchSuccess}",
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun LaunchItemDescription(launch: Launch) {
    Text(
        text = "${
            stringResource(id = R.string.launch_item_mission_text)
        } ${
            launch.missionName
        }",
        style = MaterialTheme.typography.titleSmall
    )
    Text(
        text = "${
            stringResource(id = R.string.launch_item_date_text)
        } ${
            launch.getDateAndTimeFromLaunch()
        }",
        style = MaterialTheme.typography.titleSmall
    )
    Text(
        text = "${
            stringResource(id = R.string.launch_item_rocket_text)
        } ${
            launch.rocket.rocketName
        }/${
            launch.rocket.rocketType
        }",
        style = MaterialTheme.typography.titleSmall
    )
    Spacer(modifier = Modifier.height(10.dp))
}
