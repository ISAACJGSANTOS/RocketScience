package com.project.rocketscience.data.remote

import com.project.rocketscience.data.remote.model.CompanyInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * Interface representing remote data operations for retrieving [CompanyInfo].
 * Implementations of this interface should handle network communication with an external API.
 */
interface RemoteDataSource {
    /**
     * Requests the [CompanyInfo] from a remote server or API.
     *
     * @return A [Response] containing the [CompanyInfo] or an error state.
     */
    suspend fun requestCompanyInfo(): Response<CompanyInfo>
}