package com.project.rocketscience.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.project.rocketscience.presentation.ui.components.WebView

/**
 * A composable function that serves as a wrapper to display a [WebView] for a given URL.
 *
 * This function delegates the rendering of the WebView to the [WebView] composable,
 * while exposing the parameters necessary to load content and handle navigation.
 *
 * @param url The URL to be loaded in the WebView.
 * @param onPressBack Callback to be invoked when the back button is pressed.
 *
 * This composable can be used to isolate or abstract WebView logic from higher-level UI layers.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LaunchInfoWebView(
    url: String,
    onPressBack: () -> Unit
) {
    WebView(url = url, onPressBack = onPressBack)
}
