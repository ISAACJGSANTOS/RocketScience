package com.project.rocketscience.domain

import android.util.Log
import com.project.rocketscience.data.remote.model.CompanyInfo
import com.project.rocketscience.data.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
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
    operator fun invoke(): Flow<Result<CompanyInfo>>{
        return appRepository.getCompanyInfo()
            .map { Result.success(it) }
            .catch {
                Log.e("GetCompanyInfoUseCase", "Error getting the company data: $it")
            }
    }
}
