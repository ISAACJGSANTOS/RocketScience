package com.project.rocketscience.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.rocketscience.data.local.database.entity.CompanyInfoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for SpaceX Company Information.
 *
 * This interface defines the database interactions for the [CompanyInfoEntity] object,
 * such as querying and inserting company information. Room will generate the
 * necessary implementation for these methods.
 */
@Dao
interface CompanyInfoDao {
    /**
     * Retrieves the company information from the 'company_info' table as a [Flow].
     * @return A [Flow] that emits the [CompanyInfoEntity] object from the database.
     */
    @Query("SELECT * FROM company_info")
    fun getCompanyInfo(): Flow<CompanyInfoEntity>

    /**
     * Inserts or replaces the [CompanyInfoEntity] object in the database.
     * @param companyInfoEntity The [CompanyInfoEntity] object to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCompanyInfo(companyInfoEntity: CompanyInfoEntity)
}
