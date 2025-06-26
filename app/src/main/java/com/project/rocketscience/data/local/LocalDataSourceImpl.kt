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
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val companyInfoDao: CompanyInfoDao,
    private val launchDao: LaunchDao
) : LocalDataSource {
    override suspend fun getCompanyInfoFromDatabase(): Flow<CompanyInfo> {
        return withContext(Dispatchers.IO) {
            companyInfoDao.getCompanyInfo().mapNotNull { entity -> entity?.toCompanyInfo() }
        }
    }

    override suspend fun saveCompanyInfoToDatabase(companyInfo: CompanyInfo) {
        withContext(Dispatchers.IO) {
            companyInfoDao.saveCompanyInfo(companyInfo.toCompanyInfoEntity())
        }
    }

    override suspend fun getLaunchesFromDatabase(): Flow<List<Launch>> {
        return withContext(Dispatchers.IO) {
            launchDao.getListOfLaunches()
                .mapNotNull { storedLaunches ->
                    storedLaunches.map { launchEntity -> launchEntity.toLaunch() }
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
