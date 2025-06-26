package com.project.rocketscience.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.rocketscience.R
import java.time.LocalDate

/**
 * A dialog that allows users to filter launch data by year and sort order.
 *
 * This alert dialog presents a list of years (from 2006 to the current year) as checkboxes,
 * allowing multiple selection. It also includes a checkbox to toggle sorting in descending order.
 * When the user confirms, the selected filter values are passed back via the [onApplyFilterButtonClick] callback.
 *
 * @param onApplyFilterButtonClick Callback invoked when the user confirms the filter.
 *        Provides a list of selected years as [List<String>] and a [Boolean] indicating
 *        whether the results should be ordered descending.
 * @param onDismissClickAction Callback invoked when the dialog is dismissed.
 */
@Composable
fun YearFilterAlertDialog(
    onApplyFilterButtonClick: (List<String>, Boolean) -> Unit,
    onDismissClickAction: () -> Unit
) {
    val filterYearsOptions = (2006..LocalDate.now().year).toList().reversed()
    val selectedYears = remember { mutableStateListOf<String>() }
    val organizeDescending = remember { mutableStateOf(false) }

    AlertDialog(
        modifier = Modifier.testTag("YearFilterDialog"),
        onDismissRequest = { onDismissClickAction() },
        title = { Text(text = stringResource(id = R.string.year_filter_title)) },
        text = {
            LazyColumn(modifier = Modifier.height(200.dp)) {

                itemsIndexed(
                    items = filterYearsOptions,
                    key = { _, year -> year }
                ) { _, year ->
                    CheckboxItem(
                        year = year.toString(),
                        selectedYears = selectedYears
                    )
                    HorizontalDivider(thickness = 2.dp)
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        onApplyFilterButtonClick(selectedYears, organizeDescending.value)
                        onDismissClickAction()
                    }
                ) {
                    Text(text = stringResource(id = R.string.year_filter_confirmation_button))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DescendingOrderCheckBox(
                    isChecked = organizeDescending.value,
                    onCheckBoxSelected = { isChecked ->
                        organizeDescending.value = isChecked
                    }
                )
            }
        }
    )
}

@Composable
private fun CheckboxItem(
    year: String,
    selectedYears: MutableList<String>,
) {
    val isYearSelected = selectedYears.contains(year)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Checkbox(
            modifier = Modifier.testTag("CheckboxPicker_$year"),
            checked = isYearSelected,
            onCheckedChange = { isChecked ->
                if (isChecked) {
                    selectedYears.add(year)
                } else {
                    selectedYears.remove(year)
                }
            }
        )
        Text(text = year)
    }
}

@Composable
private fun DescendingOrderCheckBox(isChecked: Boolean, onCheckBoxSelected: (Boolean) -> Unit) {
    Checkbox(
        modifier = Modifier.testTag("DescendingOrderCheckbox"),
        checked = isChecked,
        onCheckedChange = { check ->
            onCheckBoxSelected(check)
        }
    )
    Text(text = stringResource(id = R.string.year_filter_descending_checkbox_text))
}
