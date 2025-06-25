package com.project.rocketscience.domain

import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * A Use Case responsible for retrieving SpaceX company information.
 *
 * This Use Case acts as an intermediary between the presentation layer.
 * and the data layer (represented by [AppRepository]). It encapsulates the specific.
 *
 * @property appRepository The [AppRepository] from which to fetch the company information.
 */
class GetCompanyInfoUseCase @Inject constructor(private val appRepository: AppRepository) {

    /**
     * Executes the Use Case to get company information.
     *
     * When invoked, this function calls the [AppRepository.getCompanyInfo] method
     * to fetch the data. The data is returned as a [Flow] of [Result]
     *
     * @return A [Flow] that emits a [Result] object.
     *         - On success, the [Result] will contain the [CompanyInfo].
     *         - On failure, the [Result] will contain an [Exception] detailing the error.
     */
    suspend operator fun invoke(): Flow<Result<CompanyInfo>> = appRepository.getCompanyInfo()
}
