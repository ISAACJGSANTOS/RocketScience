package com.project.rocketscience.core

/**
 * A sealed class representing various types of application-specific errors.
 * This allows for precise error handling and mapping to user-friendly messages
 * at the presentation layer.
 *
 * @param message A message for the error.
 * @param cause The original Throwable that caused this error, if any.
 */
sealed class AppException(message: String?, cause: Throwable? = null) : Throwable(message, cause) {

    /**
     * Represents a network connectivity issue (e.g., no internet connection).
     */
    class NetworkException(message: String = "No internet connection", cause: Throwable? = null) : AppException(message, cause)

    /**
     * Represents an error returned by an API (e.g., 400, 401, 404, 500 HTTP status codes).
     * @param code The HTTP status code.
     */
    class ApiException(val code: Int, message: String, cause: Throwable? = null) : AppException(message, cause)

    /**
     * Represents an error during a database operation.
     */
    class DatabaseException(message: String = "Database error occurred", cause: Throwable? = null) : AppException(message, cause)

    /**
     * Represents a situation where requested data was not found in the cache.
     */
    class CacheMissException(message: String = "No cached data available", cause: Throwable? = null) : AppException(message, cause)

    /**
     * Represents any other unexpected or unhandled error.
     */
    class UnknownException(message: String = "An unknown error occurred", cause: Throwable? = null) : AppException(message, cause)
}
