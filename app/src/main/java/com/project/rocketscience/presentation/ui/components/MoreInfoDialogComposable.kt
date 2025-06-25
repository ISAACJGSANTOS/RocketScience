package com.project.rocketscience.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.rocketscience.R

/**
 * A dialog that provides users with options to learn more about a rocket launch.
 *
 * This dialog includes two buttons: one to open a Wikipedia page and another to open a YouTube video,
 * along with a dismiss handler. It is useful for presenting additional information about a launch.
 *
 * @param onYoutubeButtonClickAction Callback triggered when the YouTube button is clicked.
 * @param onWikipediaButtonClickAction Callback triggered when the Wikipedia button is clicked.
 * @param onDismissClickAction Callback triggered when the dialog is dismissed.
 */
@Composable
fun MoreInfoDialog(
    onYoutubeButtonClickAction: () -> Unit,
    onWikipediaButtonClickAction: () -> Unit,
    onDismissClickAction: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.testTag("MoreInfoDialog"),
        onDismissRequest = { onDismissClickAction() },
        title = { Text(text = stringResource(id = R.string.more_info_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.more_info_dialog_description)) },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        onDismissClickAction()
                        onWikipediaButtonClickAction()
                    }
                ) {
                    Text(text = stringResource(id = R.string.more_info_dialog_first_button_text))
                }
                Button(
                    onClick = {
                        onDismissClickAction()
                        onYoutubeButtonClickAction()
                    }
                ) {
                    Text(text = stringResource(id = R.string.more_info_dialog_second_button_text))
                }
            }
        }
    )
}
