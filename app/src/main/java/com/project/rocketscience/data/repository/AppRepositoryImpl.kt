package com.project.rocketscience.data.repository

import com.project.rocketscience.core.AppException
import com.project.rocketscience.data.local.LocalDataSource
import com.project.rocketscience.data.remote.RemoteDataSource
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AppRepository {

    override fun getCompanyInfo(): Flow<CompanyInfo> {
        return flow {
            try {
                val companyInfo = remoteDataSource.requestCompanyInfo()
                emit(companyInfo)
                localDataSource.saveCompanyInfoToDatabase(companyInfo)
            } catch (e: AppException) {
                when(e){
                    is AppException.ApiException,
                    is AppException.NetworkException -> {
                        val companyInfoCache = localDataSource.getCompanyInfoFromDatabase()
                        emitAll(companyInfoCache)
                    }
                    else -> throw e
                }
            } catch (e: Exception) {
                throw AppException.UnknownException(cause = e)
            }
        }
    }

    override fun getLaunches(): Flow<List<Launch>> {
        return flow {
            try {
                val launches = remoteDataSource.requestLaunches()
                emit(launches)
                localDataSource.saveLaunchesToDatabase(launches)
            } catch (e: AppException) {
                when(e){
                    is AppException.ApiException,
                    is AppException.NetworkException -> {
                        val launchesCache = localDataSource.getLaunchesFromDatabase()
                        emitAll(launchesCache)
                    }
                    else -> throw e
                }
            } catch (e: Exception) {
                throw AppException.UnknownException(cause = e)
            }
        }
    }
}

