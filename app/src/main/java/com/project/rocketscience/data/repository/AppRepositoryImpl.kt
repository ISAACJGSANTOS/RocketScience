package com.project.rocketscience.data.repository

import com.project.rocketscience.data.local.LocalDataSource
import com.project.rocketscience.data.remote.RemoteDataSource
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AppRepository {

    override suspend fun getCompanyInfo(): Flow<Result<CompanyInfo>> {
        return flow {
            fetchData {
                remoteDataSource.requestCompanyInfo()
            }.onSuccess { companyInfo ->
                emit(Result.success(companyInfo))
                localDataSource.saveCompanyInfoToDatabase(companyInfo = companyInfo)
            }.onFailure { error ->
                emit(Result.failure(error))
                emitAll(
                    localDataSource.getCompanyInfoFromDatabase().map { companyInfo ->
                        Result.success(companyInfo)
                    }
                )
            }
        }
    }

    override suspend fun getLaunches(): Flow<Result<List<Launch>>> {
        return flow {
            fetchData {
                remoteDataSource.requestLaunches()
            }.onSuccess { response ->
                emit(Result.success(response))
                localDataSource.saveLaunchesToDatabase(launchesList = response)
            }.onFailure { error ->
                emit(Result.failure(error))
                emitAll(
                    localDataSource.getLaunchesFromDatabase().map { launches ->
                        Result.success(launches)
                    }
                )
            }
        }
    }

    override suspend fun getFilteredLaunches(
        filterYearsList: List<String>,
        organizeDescending: Boolean
    ): Flow<Result<List<Launch>>> {
        return flow {
            fetchData {
                remoteDataSource.requestLaunches()
            }.onSuccess { launches ->
                emit(
                    Result.success(
                        applyLaunchFilter(
                            launchesList = launches,
                            filterYears = filterYearsList,
                            organizeDescending = organizeDescending
                        )
                    )
                )
                localDataSource.saveLaunchesToDatabase(launchesList = launches)
            }.onFailure { error ->
                emit(Result.failure(error))
                emitAll(
                    localDataSource.getLaunchesFromDatabase().map { launches ->
                        Result.success(
                            applyLaunchFilter(
                                launchesList = launches,
                                filterYears = filterYearsList,
                                organizeDescending = organizeDescending
                            )
                        )
                    }
                )
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

    /**
     * Filters and optionally sorts a list of [Launch] objects based on launch year and success status.
     *
     * The function filters the input [launchesList] to include only those launches that:
     * - Match the given [filterYears], if any are provided.
     * - Were successful (`launchSuccess` is true).
     *
     * After filtering, it sorts the list by launch year in descending order if [organizeDescending] is true.
     *
     * @param launchesList The original list of launches to filter and sort.
     * @param filterYears A list of years (as Strings) to include in the result. If empty, all years are included.
     * @param organizeDescending If true, the filtered results are sorted in descending order by year.
     *
     * @return A list of filtered and optionally sorted [Launch] objects.
     */
    private fun applyLaunchFilter(
        launchesList: List<Launch>,
        filterYears: List<String>,
        organizeDescending: Boolean
    ): List<Launch> {
        val filterLaunches = launchesList
            .filter { launch ->
                when {
                    filterYears.isEmpty() -> true
                    else -> filterYears.contains(launch.launchYear) && launch.launchSuccess
                }
            }
            .let { filterList ->
                if (organizeDescending) {
                    filterList.sortedByDescending { it.launchYear.toInt() }
                } else {
                    filterList
                }
            }
        return filterLaunches
    }
}
