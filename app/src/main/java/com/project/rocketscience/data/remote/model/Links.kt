package com.project.rocketscience.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing external links related to a rocket launch mission.
 */
data class Links(
    @SerializedName("mission_patch")
    val missionPatchImage: String,
    @SerializedName("wikipedia")
    val wikipediaLink: String,
    @SerializedName("video_link")
    val videoLink: String
)
