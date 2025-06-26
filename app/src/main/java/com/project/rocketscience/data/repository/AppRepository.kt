package com.project.rocketscience.data.repository

import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines application-level data operations,
 * serving as a single source of truth by coordinating local and remote data sources.
 */
interface AppRepository {
    /**
     * Retrieves the [CompanyInfo] for the application.
     * This may involve reading from a local database or making a remote API call.
     *
     * @return A [Flow] emitting [Result] containing [CompanyInfo], which could represent
     *         a success, loading, or error state.
     */
    suspend fun getCompanyInfo(): Flow<Result<CompanyInfo>>

    /**
     * Retrieves a list of [Launch] data for the application.
     * This may involve reading from a local database or making a remote API call.
     *
     * @return A [Flow] emitting [Result] containing a list of [Launch], which could represent
     *         a success, loading, or error state.
     */
    suspend fun getLaunches(): Flow<Result<List<Launch>>>

    /**
     * Retrieves a filtered and optionally sorted list of [Launch] objects.
     *
     * This function fetches the list of launches and applies filtering based on the provided [filterYearsList].
     * Only launches that occurred in the specified years and were successful will be included.
     * Optionally, the resulting list can be sorted in descending order by year.
     *
     * @param filterYearsList A list of launch years to filter the launches. If empty, no filtering by year is applied.
     * @param organizeDescending If true, the results are sorted by launch year in descending order.
     *
     * @return A [Flow] emitting a [Result] that contains either the filtered list of [Launch] objects on success,
     *         or an error on failure.
     */
    suspend fun getFilteredLaunches(
        filterYearsList: List<String>,
        organizeDescending: Boolean
    ): Flow<Result<List<Launch>>>
}
