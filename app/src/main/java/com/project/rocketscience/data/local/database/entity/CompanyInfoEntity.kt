package com.project.rocketscience.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the company information entity for storage in the Room database.
 *
 * This data class defines the schema for the "company_info" table. It's designed
 * to store a single entry of SpaceX company details, hence the fixed primary key.
 *
 * Each property is annotated with [ColumnInfo] to specify the column name in the
 * database table.
 */
@Entity(tableName = "company_info")
data class CompanyInfoEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int = 1,
    @ColumnInfo("mission_name")
    val name: String,
    @ColumnInfo("founder")
    val founder: String,
    @ColumnInfo("founded")
    val founded: Int,
    @ColumnInfo("employees")
    val employees: Int,
    @ColumnInfo("launch_sites")
    val launchSites: Int,
    @ColumnInfo("valuation")
    val valuation: Long
)
