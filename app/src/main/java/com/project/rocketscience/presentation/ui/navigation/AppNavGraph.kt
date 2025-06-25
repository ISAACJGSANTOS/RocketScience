package com.project.rocketscience.presentation.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.rocketscience.presentation.ui.Dashboard
import com.project.rocketscience.presentation.ui.LaunchInfoWebView

/**
 * Sets up the main navigation graph of the application using Jetpack Compose Navigation.
 *
 * Defines navigation between the [Dashboard] screen and the [LaunchInfoWebView] screen.
 * Uses [rememberNavController] to manage navigation actions and back stack.
 */
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreenRoute.DashboardScreen.route
    ) {
        composable(AppScreenRoute.DashboardScreen.route) {
            Dashboard(
                launchInfoNavAction = { url ->
                    navigateToLaunchInfoScreen(
                        navController = navController,
                        url = url
                    )
                }
            )
        }

        composable(
            route = AppScreenRoute.LaunchInfoScreen.route,
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            LaunchInfoWebView(
                url = url,
                onPressBack = { navController.popBackStack() }
            )
        }
    }
}

/**
 * Navigates to the [LaunchInfoWebView] screen with the provided URL.
 *
 * @param navController The [NavController] used to perform the navigation.
 * @param url The URL to be passed as a parameter to the destination webpage.
 *
 * The URL is encoded using [Uri.encode] to ensure it is safely passed through the navigation route.
 * Navigation uses `launchSingleTop` to avoid multiple copies in the back stack and `restoreState` for state preservation.
 */
private fun navigateToLaunchInfoScreen(navController: NavController, url: String) {
    val route = AppScreenRoute.LaunchInfoScreen.route
        .replace("{url}", Uri.encode(url))

    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}
