package com.project.rocketscience.data.remote

import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
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

    /**
     * Requests the list of [Launch] from a remote server or API.
     *
     * @return A [Response] containing the list of [Launch] or an error state.
     */
    suspend fun requestLaunches(): Response<List<Launch>>
}
