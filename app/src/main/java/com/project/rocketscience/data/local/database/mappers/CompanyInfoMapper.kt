package com.project.rocketscience.data.local.database.mappers

import com.project.rocketscience.data.local.database.entity.CompanyInfoEntity
import com.project.rocketscience.data.remote.model.CompanyInfo

/**
 * Converts a [CompanyInfo] domain model object to a [CompanyInfoEntity] database object.
 *
 * @receiver The [CompanyInfo] object to convert.
 * @return A [CompanyInfoEntity] object populated with data from the [CompanyInfo] object.
 */
fun CompanyInfo.toCompanyInfoEntity(): CompanyInfoEntity {
    return CompanyInfoEntity(
        name = name,
        founder = founder,
        founded = founded,
        employees = employees,
        launchSites = launchSites,
        valuation = valuation
    )
}

/**
 * Converts a [CompanyInfoEntity] database object to a [CompanyInfo] domain model object.
 *
 * @receiver The [CompanyInfoEntity] object to convert.
 * @return A [CompanyInfo] object populated with data from the [CompanyInfoEntity] object.
 */
fun CompanyInfoEntity.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        name = name,
        founder = founder,
        founded = founded,
        employees = employees,
        launchSites = launchSites,
        valuation = valuation
    )
}
