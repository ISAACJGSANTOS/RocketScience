package com.project.rocketscience.data.local

import com.project.rocketscience.data.local.database.dao.CompanyInfoDao
import com.project.rocketscience.data.local.database.mappers.toCompanyInfo
import com.project.rocketscience.data.local.database.mappers.toCompanyInfoEntity
import com.project.rocketscience.data.remote.model.CompanyInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val companyInfoDao: CompanyInfoDao
): LocalDataSource {
    override suspend fun getCompanyInfoFromDatabase(): Flow<CompanyInfo> {
        return flow {
            companyInfoDao.getCompanyInfo().collect { companyInfoEntity ->
                val companyInfo = companyInfoEntity.toCompanyInfo()
                emit(companyInfo)
            }
        }
    }

    override suspend fun saveCompanyInfoToDatabase(companyInfo: CompanyInfo) {
        withContext(Dispatchers.IO){
            companyInfoDao.saveCompanyInfo(companyInfo.toCompanyInfoEntity())
        }
    }
}
