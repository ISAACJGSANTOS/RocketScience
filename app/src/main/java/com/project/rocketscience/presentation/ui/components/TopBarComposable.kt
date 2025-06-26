package com.project.rocketscience.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import com.project.rocketscience.R

/**
 * A customizable top app bar with optional navigation and filter icons.
 *
 * This composable displays a center-aligned top app bar with a title.
 * It allows the inclusion of a back navigation icon and a filter icon
 * based on the provided flags. Clicking on these icons triggers their
 * respective callbacks.
 *
 * @param title The title text to be displayed in the app bar.
 * @param showNavigationIcon If `true`, displays a back navigation icon on the left side.
 * @param onNavigationIconClick Callback invoked when the navigation icon is clicked.
 * @param showFilterIcon If `true`, displays a filter icon on the right side.
 * @param onFilterIconClick Callback invoked when the filter icon is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    showNavigationIcon: Boolean = false,
    onNavigationIconClick: () -> Unit = {},
    showFilterIcon: Boolean = false,
    onFilterIconClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(
                onClick = { onFilterIconClick() }
            ) {
                if (showFilterIcon) {
                    Icon(
                        modifier = Modifier.testTag("FilterIcon"),
                        imageVector = ImageVector.vectorResource(R.drawable.filter_icon),
                        contentDescription = "Filter Icon",
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.testTag("BackButton"),
                onClick = { onNavigationIconClick() }
            ) {
                if (showNavigationIcon) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigation back button"
                    )
                }
            }
        }
    )
}
