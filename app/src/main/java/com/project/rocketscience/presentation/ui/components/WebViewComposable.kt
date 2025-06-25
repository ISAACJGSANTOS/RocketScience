package com.project.rocketscience.presentation.ui.components

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView

/**
 * A composable that displays a WebView for a given URL with a top app bar
 * and a [CircularLoading] progress indicator shown while the page is loading.
 *
 * @param url The URL to load in the WebView.
 * @param onPressBack Lambda to be called when the back button is pressed.
 *
 * This composable uses:
 * - [WebView] from the Android SDK, integrated via [AndroidView]
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    url: String,
    onPressBack: () -> Unit
) {
    val isLoading = remember { mutableStateOf(true) }
    BackHandler {
        onPressBack()
    }
    Scaffold(
        modifier = Modifier.testTag("LaunchInfoWebView"),
        topBar = {
            TopAppBar(
                title = "",
                showNavigationIcon = true,
                onNavigationIconClick = onPressBack
            )
        }
    ) { innerPadding ->
        if (isLoading.value) {
            CircularLoading()
        }
        AndroidView(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(innerPadding),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            view?.loadUrl(request?.url.toString())
                            return true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading.value = false
                            super.onPageFinished(view, url)
                        }
                    }
                    setWebContentsDebuggingEnabled(true)
                    loadUrl(url)
                }
            }
        )
    }
}
