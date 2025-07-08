package com.project.rocketscience.presentation.ui.state

import androidx.annotation.StringRes


/**
 * Represents the UI state for a screen or component.
 *
 * This sealed class models the common states that a UI can be in:
 * - [Success]: The operation completed successfully.
 * - [Error]: An error occurred, with a descriptive [message].
 * - [Loading]: The operation is currently in progress.
 */
sealed class UiState {
    /**
     * Indicates that the operation was successful and the UI can reflect the result.
     */
    data object Success : UiState()

    /**
     * Represents an error state with an associated message describing the issue.
     *
     * @param stringResource A error message to display to the user.
     */
    data class Error(@StringRes val stringResource: Int) : UiState()

    /**
     * Indicates that a background operation (such as loading data) is in progress.
     */
    data object Loading : UiState()
}
