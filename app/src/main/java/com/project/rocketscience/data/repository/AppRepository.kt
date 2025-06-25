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
}
