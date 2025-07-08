package com.project.rocketscience.data.remote

import com.project.rocketscience.core.AppException
import com.project.rocketscience.data.remote.api.SpaceXApi
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val spaceXApi: SpaceXApi
) : RemoteDataSource {
    override suspend fun requestCompanyInfo(): CompanyInfo {
        return fetchData {
            withContext(Dispatchers.IO) {
                spaceXApi.getCompanyInfo()
            }
        }
    }

    override suspend fun requestLaunches(): List<Launch> {
        return fetchData {
            withContext(Dispatchers.IO) {
                spaceXApi.getLaunches()
            }
        }
    }

    private suspend fun <T> fetchData(apiCall: suspend () -> Response<T>): T {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                return response.body() ?: throw AppException.ApiException(
                    code = response.code(),
                    message = "API call succeeded but response body was null.",
                    cause = null
                )
            } else {
                throw AppException.ApiException(
                    code = response.code(),
                    message = response.errorBody()?.string() ?: response.message(),
                    cause = HttpException(response)
                )
            }
        } catch (e: IOException) {
            throw AppException.NetworkException(e.message ?: "No internet connection", e)
        } catch (e: HttpException) {
            throw AppException.ApiException(
                code = e.code(),
                message = e.response()?.errorBody()?.string() ?: e.message(),
                cause = e
            )
        } catch (e: Exception) {
            throw AppException.UnknownException("An unknown error occurred during API call: ${e.message}", e)
        }
    }
}
