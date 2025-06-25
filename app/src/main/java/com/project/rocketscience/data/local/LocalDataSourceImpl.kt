package com.project.rocketscience.data.local

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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val companyInfoDao: CompanyInfoDao,
    private val launchDao: LaunchDao
) : LocalDataSource {
    override suspend fun getCompanyInfoFromDatabase(): Flow<CompanyInfo> {
        return flow {
            companyInfoDao.getCompanyInfo().collect { companyInfoEntity ->
                val companyInfo = companyInfoEntity.toCompanyInfo()
                emit(companyInfo)
            }
        }
    }

    override suspend fun saveCompanyInfoToDatabase(companyInfo: CompanyInfo) {
        withContext(Dispatchers.IO) {
            companyInfoDao.saveCompanyInfo(companyInfo.toCompanyInfoEntity())
        }
    }

    override suspend fun getLaunchesFromDatabase(): Flow<List<Launch>> {
        return flow {
            launchDao.getListOfLaunches().collect { storedLaunches ->
                val launches = storedLaunches.map { launchEntity -> launchEntity.toLaunch() }
                emit(launches)
            }
        }
    }

    override suspend fun saveLaunchesToDatabase(launchesList: List<Launch>) {
        withContext(Dispatchers.IO) {
            val launches = launchesList.map { launch -> launch.toLaunchEntity() }
            launchDao.saveLastListOfLaunches(launches)
        }
    }
}
