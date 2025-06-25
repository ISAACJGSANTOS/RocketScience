package com.project.rocketscience.data.repository

import com.project.rocketscience.data.remote.model.CompanyInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

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
}
