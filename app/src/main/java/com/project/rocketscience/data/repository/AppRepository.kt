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
     * @return A [Flow] containing [CompanyInfo]
     */
    fun getCompanyInfo(): Flow<CompanyInfo>

    /**
     * Retrieves a list of [Launch] data for the application.
     * This may involve reading from a local database or making a remote API call.
     *
     * @return A [Flow]  containing a list of [Launch]
     */
    fun getLaunches(): Flow<List<Launch>>
}
