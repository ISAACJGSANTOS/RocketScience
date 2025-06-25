# SpaceXLaunch Tracker

A simple Android application built with Kotlin and Jetpack Compose that displays a list of SpaceX launches.
It fetches data from the [SpaceX REST API](https://github.com/r-spacex/SpaceX-API/tree/master/docs#rspacex-api-docs) and uses Room for basic offline caching.

This project serves as an exercise to demonstrate the use of modern Android development technologies.

## ✨ Core Features

*   View a list of SpaceX launches.
*   Offline caching of launch data.
*   Links to Wikipedia and YouTube for more launch details.
*   Basic company information display.

## 🛠️ Tech Stack & Key Libraries

*   **Kotlin:**
*   **Jetpack Compose:** For the UI.
*   **MVVM Architecture**
*   **Coroutines & Flow:** For asynchronous operations.
*   **Hilt:** For dependency injection.
*   **Retrofit & OkHttp:** For networking.
*   **Room:** For local database storage.
*   **Coil:** For image loading.
*   **Unit & Instrumented Tests:**

## 🚀 Getting Started

1  **Clone the repository:**
2  **Open in Android Studio.**
3  **Run the app** (▶️).

No special configuration is needed.

## 🧪 Running Tests

*   **Unit Tests:** `./gradlew testDebugUnitTest`
*   **Instrumented Tests:** `./gradlew connectedDebugAndroidTest`

You can also run tests directly from Android Studio by right-clicking on test files/directories in the `app/src/test` and `app/src/androidTest` folders.

