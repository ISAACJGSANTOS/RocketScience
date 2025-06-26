package com.project.rocketscience.domain

import com.project.rocketscience.data.remote.model.Launch
import com.project.rocketscience.data.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving a filtered and optionally sorted list of [Launch] objects.
 *
 * This use case delegates the filtering logic to the [AppRepository], applying the given year filters
 * and ordering based on the provided parameters.
 *
 * @property appRepository The repository responsible for data access and filtering logic.
 */
class GetFilteredLaunchesUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(
        filterYearsList: List<String>,
        organizeDescending: Boolean
    ): Flow<Result<List<Launch>>> = appRepository
        .getFilteredLaunches(filterYearsList, organizeDescending)
}
