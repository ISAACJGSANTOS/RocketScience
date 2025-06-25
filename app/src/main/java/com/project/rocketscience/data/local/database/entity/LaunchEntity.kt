package com.project.rocketscience.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a SpaceX launch entity for storage in the Room database.
 *
 * This data class defines the schema for the "launches" table. Each instance
 * corresponds to a single launch event. The `missionName` is used as the
 * [PrimaryKey] for uniquely identifying each launch.
 *
 */
@Entity(tableName = "launches")
data class LaunchEntity(
    @PrimaryKey()
    @ColumnInfo("mission_name")
    val missionName: String,
    @ColumnInfo("upcoming")
    val upcoming: Boolean,
    @ColumnInfo("launch_year")
    val launchYear: String,
    @ColumnInfo("date_unix")
    val dateUnix: Long,
    @ColumnInfo("launch_success")
    val launchSuccess: Boolean,
    @ColumnInfo("rocket_name")
    val rocketName: String,
    @ColumnInfo("rocket_type")
    val rocketType: String,
    @ColumnInfo("mission_patch_image")
    val missionPatchImage: String?,
    @ColumnInfo("wikipedia_link")
    val wikipediaLink: String?,
    @ColumnInfo("video_link")
    val videoLink: String?
)
