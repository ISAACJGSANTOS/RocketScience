package com.project.rocketscience.presentation.ui.navigation

/**
 * Sealed class that defines the navigation routes used in the application.
 *
 * Each route represents a distinct screen and may include route parameters
 * for dynamic navigation (e.g., passing a URL).
 *
 * @property route The string representation of the route, used in the navigation graph.
 */
sealed class AppScreenRoute(val route: String) {
    /**
     * Route for the Dashboard screen, which displays a list of space launches
     * and company information.
     */
    data object DashboardScreen : AppScreenRoute("dashboard_screen")

    /**
     * Route for the Launch Info WebView screen, which shows detailed information
     * about a selected launch
     */
    data object LaunchInfoScreen : AppScreenRoute("launch_info_screen/{url}")
}
