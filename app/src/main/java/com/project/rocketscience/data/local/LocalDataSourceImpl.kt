package com.project.rocketscience.data.local

import com.project.rocketscience.core.AppException
import com.project.rocketscience.data.local.database.dao.CompanyInfoDao
import com.project.rocketscience.data.local.database.dao.LaunchDao
import com.project.rocketscience.data.local.database.mappers.toCompanyInfo
import com.project.rocketscience.data.local.database.mappers.toCompanyInfoEntity
import com.project.rocketscience.data.local.database.mappers.toLaunch
import com.project.rocketscience.data.local.database.mappers.toLaunchEntity
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.remote.model.Launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val companyInfoDao: CompanyInfoDao,
    private val launchDao: LaunchDao
) : LocalDataSource {
    override fun getCompanyInfoFromDatabase(): Flow<CompanyInfo> {
        return companyInfoDao.getCompanyInfo()
            .map { companyInfoEntity -> companyInfoEntity.toCompanyInfo() }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun saveCompanyInfoToDatabase(companyInfo: CompanyInfo) {
        withContext(Dispatchers.IO) {
            try {
                companyInfoDao.saveCompanyInfo(companyInfo.toCompanyInfoEntity())
            } catch (e: Exception) {
                throw AppException.DatabaseException(cause = e)
            }
        }
    }

    override fun getLaunchesFromDatabase(): Flow<List<Launch>> {
        return launchDao.getListOfLaunches()
            .map { storedLaunches ->
                if (storedLaunches.isEmpty()) {
                    throw AppException.CacheMissException()
                } else {
                    storedLaunches.map { launchEntity -> launchEntity.toLaunch() }
                }
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveLaunchesToDatabase(launchesList: List<Launch>) {
        withContext(Dispatchers.IO) {
            try {
                val launches = launchesList.map { launch -> launch.toLaunchEntity() }
                launchDao.saveLastListOfLaunches(launches)
            } catch (e: Exception) {
                throw AppException.DatabaseException(cause = e)
            }
        }
    }
}
