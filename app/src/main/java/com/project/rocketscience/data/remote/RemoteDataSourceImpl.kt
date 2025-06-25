package com.project.rocketscience.data.remote

import com.project.rocketscience.data.remote.api.SpaceXApi
import com.project.rocketscience.data.remote.model.CompanyInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val spaceXApi: SpaceXApi
): RemoteDataSource {
    override suspend fun requestCompanyInfo(): Response<CompanyInfo> {
        return spaceXApi.getCompanyInfo()
    }
}
