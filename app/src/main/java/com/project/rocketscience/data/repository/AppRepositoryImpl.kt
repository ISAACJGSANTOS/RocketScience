package com.project.rocketscience.data.repository

import com.project.rocketscience.data.local.LocalDataSource
import com.project.rocketscience.data.remote.RemoteDataSource
import com.project.rocketscience.data.remote.model.CompanyInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): AppRepository {

    override suspend fun getCompanyInfo(): Flow<Result<CompanyInfo>> {
        return flow {
            fetchData {
                remoteDataSource.requestCompanyInfo()
            }.onSuccess { companyInfo ->
                emit(Result.success(companyInfo))
                localDataSource.saveCompanyInfoToDatabase(companyInfo = companyInfo)
            }.onFailure { error ->
                emit(Result.failure(error))
                localDataSource.getCompanyInfoFromDatabase().collect { companyInfo ->
                    emit(Result.success(companyInfo))
                }
            }
        }
    }

    /**
     * A generic helper function to safely execute a remote API call.
     * It wraps the response in a [Result], handling errors such as exceptions or HTTP failures.
     *
     * @param apiCall A suspend lambda representing the network request to perform.
     * @return A [Result] object containing the response body or an error.
     */
    private suspend fun <T> fetchData(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("API call failed: ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("No internet connection"))
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Unable to resolve the host. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Unexpected error occurred"))
        }
    }
}
