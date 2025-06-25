package com.project.rocketscience.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.project.rocketscience.R

/**
 * Displays an error [AlertDialog] with a message and a single confirmation button.
 *
 * The dialog is shown whenever a non-empty [error] string is provided,
 * and remains visible until dismissed by the user.
 *
 * @param error The error message to display inside the dialog.
 * @param onButtonClickAction A callback invoked when the user clicks the "OK" button.
 *
 */
@Composable
fun ErrorAlertDialog(
    error: String,
    onButtonClickAction: () -> Unit
) {
    val showDialogError = remember { mutableStateOf(true) }
    LaunchedEffect(error) {
        showDialogError.value = true
    }
    if (showDialogError.value) {
        AlertDialog(modifier = Modifier.testTag("ErrorAlertDialog"),
            onDismissRequest = {
                showDialogError.value = false
            },
            title = { Text(text = stringResource(id = R.string.error_dialog_title)) },
            text = { Text(text = error) },
            confirmButton = {
                Button(onClick = {
                    showDialogError.value = false
                    onButtonClickAction()
                }) {
                    Text(stringResource(id = R.string.error_dialog_button_text))
                }
            }
        )
    }
}
