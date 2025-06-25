package com.project.rocketscience.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.rocketscience.data.local.database.dao.CompanyInfoDao
import com.project.rocketscience.data.local.database.entity.CompanyInfoEntity

/**
 * The main Room database class for the application.
 *
 * This abstract class extends [RoomDatabase] and serves as the central access point
 * to the persisted data. It defines the entities included in the database,
 * the database version, and provides abstract methods to get Data Access Objects (DAOs).
 *
 * The `@Database` annotation includes:
 *  - `entities`: An array of all entity classes that are part of this database.
 *  - `version`: The version of the database schema. This must be incremented when
 *               the schema changes, and a migration strategy must be provided.*  - `exportSchema`: A boolean indicating whether to export the database schema into a
 *                    JSON file in the project directory. It's set to `false` here,
 *                    which is common for projects not intending to share or version
 *                    control the schema file directly, though for complex projects,
 *                    exporting the schema can be useful for migrations and debugging.
 */
@Database(
    entities = [CompanyInfoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getCompanyInfo(): CompanyInfoDao
}
