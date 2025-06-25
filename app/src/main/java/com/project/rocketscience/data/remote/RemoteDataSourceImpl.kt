package com.project.rocketscience.data.remote

import com.project.rocketscience.data.remote.api.SpaceXApi
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val spaceXApi: SpaceXApi
) : RemoteDataSource {
    override suspend fun requestCompanyInfo(): Response<CompanyInfo> {
        return spaceXApi.getCompanyInfo()
    }

    override suspend fun requestLaunches(): Response<List<Launch>> {
        return spaceXApi.getLaunches()
    }
}
