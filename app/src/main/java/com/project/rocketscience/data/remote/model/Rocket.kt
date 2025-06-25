package com.project.rocketscience.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a SpaceX rocket.
 */
data class Rocket(
    @SerializedName("rocket_name")
    val rocketName: String,
    @SerializedName("rocket_type")
    val rocketType: String
)
