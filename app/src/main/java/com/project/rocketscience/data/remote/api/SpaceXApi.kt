package com.project.rocketscience.data.remote.api

import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
import retrofit2.Response
import retrofit2.http.GET

/**
 * Defines the API endpoints for interacting with the SpaceX REST API.
 */
interface SpaceXApi {

    /**
     * Fetches general information about the SpaceX company from the remote server.
     */
    @GET("info")
    suspend fun getCompanyInfo(): Response<CompanyInfo>

    /**
     * Fetches a list of all SpaceX launches from the remote server.
     */
    @GET("launches")
    suspend fun getLaunches(): Response<List<Launch>>
}
