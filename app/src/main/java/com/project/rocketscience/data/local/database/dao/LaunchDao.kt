package com.project.rocketscience.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.rocketscience.data.local.database.entity.LaunchEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for SpaceX Launch entities.
 *
 * This interface defines the database interactions for [LaunchEntity] objects,
 * such as querying and inserting launch data. Room will generate the
 * necessary implementation for these methods.
 */
@Dao
interface LaunchDao {
    /**
     * Retrieves all launches from the 'launches' table as a [Flow].
     * @return A [Flow] that emits a list of all [LaunchEntity]objects in the database.
     */
    @Query("SELECT * FROM launches")
    fun getListOfLaunches(): Flow<List<LaunchEntity>>

    /**
     * Inserts a list of [LaunchEntity] objects into the database.
     * @param launchEntityList The list of [LaunchEntity] objects to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLastListOfLaunches(launchEntityList: List<LaunchEntity>)
}
