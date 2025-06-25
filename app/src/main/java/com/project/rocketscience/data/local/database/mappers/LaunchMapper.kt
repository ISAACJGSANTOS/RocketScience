package com.project.rocketscience.data.local.database.mappers

import com.project.rocketscience.data.local.database.entity.LaunchEntity
import com.project.rocketscience.data.remote.model.Launch
import com.project.rocketscience.data.remote.model.Links
import com.project.rocketscience.data.remote.model.Rocket

/**
 * Converts a [Launch] domain model object to a [LaunchEntity] database object.
 *
 * @receiver The [Launch] object to convert.
 * @return A [LaunchEntity] object populated with data from the [Launch] object.
 */
fun Launch.toLaunchEntity(): LaunchEntity {
    return LaunchEntity(
        missionName = missionName,
        upcoming = upcoming,
        launchYear = launchYear,
        dateUnix = dateUnix,
        launchSuccess = launchSuccess,
        rocketName = rocket.rocketName,
        rocketType = rocket.rocketType,
        missionPatchImage = links.missionPatchImage,
        wikipediaLink = links.wikipediaLink,
        videoLink = links.videoLink
    )
}

/**
 * Converts a [LaunchEntity] database object to a [Launch] domain model object.
 *
 * @receiver The [LaunchEntity] object to convert.
 * @return A [Launch] object populated with data from the [LaunchEntity] object.
 */
fun LaunchEntity.toLaunch(): Launch {
    return Launch(
        missionName = missionName,
        upcoming = upcoming,
        launchYear = launchYear,
        dateUnix = dateUnix,
        rocket = Rocket(rocketName = rocketName, rocketType = rocketType),
        launchSuccess = launchSuccess,
        links = Links(
            missionPatchImage = missionPatchImage ?: "",
            wikipediaLink = wikipediaLink ?: "",
            videoLink = videoLink ?: ""
        )
    )
}
