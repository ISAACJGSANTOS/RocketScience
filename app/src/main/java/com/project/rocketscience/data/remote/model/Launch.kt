package com.project.rocketscience.data.remote.model

import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Represents a single SpaceX launch event.
 *
 ** This data class models the detailed information for a specific launch.
 */
data class Launch(
    @SerializedName("mission_name")
    val missionName: String,
    @SerializedName("upcoming")
    val upcoming: Boolean,
    @SerializedName("launch_year")
    val launchYear: String,
    @SerializedName("launch_date_unix")
    val dateUnix: Long,
    @SerializedName("rocket")
    val rocket: Rocket,
    @SerializedName("launch_success")
    val launchSuccess: Boolean,
    @SerializedName("links")
    val links: Links
) {

    /**
     * Returns a formatted string representation of the rocket launch date and time.
     *
     * The launch date is derived from a Unix timestamp and converted from UTC time zone.*
     * @return A string in the format: "Date/Time: MM/dd/yy at HH:mm"
     */
    fun getDateAndTimeFromLaunch(): String {
        val dateInstant = Instant.ofEpochSecond(dateUnix).atZone(ZoneId.of("UTC"))
        val time = dateInstant.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
        val dateAndTime = time.replace(", ", " at ")
        return dateAndTime
    }
}
