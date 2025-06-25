package com.project.rocketscience.data.local

import com.project.rocketscience.data.remote.model.CompanyInfo
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing local data operations related to [CompanyInfo].
 * Implementations of this interface should handle interaction with a local database or storage.
 */
interface LocalDataSource {
    /**
     * Retrieves the [CompanyInfo] stored in the local database.
     *
     * @return A [Flow] that emits the stored [CompanyInfo].
     */
    suspend fun getCompanyInfoFromDatabase(): Flow<CompanyInfo>

    /**
     * Saves the given [CompanyInfo] into the local database.
     *
     * @param companyInfo The [CompanyInfo] to be persisted locally.
     */

    suspend fun saveCompanyInfoToDatabase(companyInfo: CompanyInfo)
}
