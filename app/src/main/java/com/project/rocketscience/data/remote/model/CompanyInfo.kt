package com.project.rocketscience.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Represents SpaceX company information.
 *
 ** This data class is designed to model the structure of company data, often from a JSON response.
 */
data class CompanyInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("founder")
    val founder: String,
    @SerializedName("founded")
    val founded: Int,
    @SerializedName("employees")
    val employees: Int,
    @SerializedName("launch_sites")
    val launchSites: Int,
    @SerializedName("valuation")
    val valuation: Long
) {

    /**
     * Builds a description of the company based on its key properties.
     *
     * The description includes the company's name, founder, founding year, number of employees,
     * number of launch sites, and current valuation.
     *
     */
    fun getCompanyDescription(): String {
        return "${name}, was founded by $founder in $founded. " +
                "It has now $employees employees, " +
                "$launchSites launch sites, and is valued at USD $valuation"
    }
}
