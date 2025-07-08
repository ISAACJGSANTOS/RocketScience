package com.project.rocketscience.domain

import android.util.Log
import com.project.rocketscience.core.AppException
import com.project.rocketscience.data.remote.model.Launch
import com.project.rocketscience.data.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for retrieving a filtered and optionally sorted list of [Launch] objects.
 * This Use Case acts as an intermediary between the presentation layer.
 * and the data layer (represented by [AppRepository]). It encapsulates the specific
 * business logic for fetching and filtering the launch information.
 *
 */
class GetFilteredLaunchesUseCase @Inject constructor(private val appRepository: AppRepository) {
    operator fun invoke(
        filterYearsList: List<String>,
        organizeDescending: Boolean
    ): Flow<Result<List<Launch>>> {
        return appRepository.getLaunches()
            .map { launches ->
                val filteredLaunches = applyLaunchFilter(
                    launchesList = launches,
                    filterYears = filterYearsList,
                    organizeDescending = organizeDescending
                )
                Result.success(
                    filteredLaunches
                )
            }
            .catch {
                when (it) {
                    is AppException.CacheMissException -> {
                        emit(Result.failure(it))
                    }

                    else -> {
                        Log.e(
                            "GetFilteredLaunchesUseCase",
                            "Error getting the launches data: $it"
                        )
                    }
                }
            }
    }


    /**
     * Filters and optionally sorts a list of [Launch] objects based on launch year and success status.
     *
     * The function filters the input [launchesList] to include only those launches that:
     * - Match the given [filterYears], if any are provided.
     * - Were successful (`launchSuccess` is true).
     *
     * After filtering, it sorts the list by launch year in descending order if [organizeDescending] is true.
     *
     * @param launchesList The original list of launches to filter and sort.
     * @param filterYears A list of years (as Strings) to include in the result. If empty, all years are included.
     * @param organizeDescending If true, the filtered results are sorted in descending order by year.
     *
     * @return A list of filtered and optionally sorted [Launch] objects.
     */
    private fun applyLaunchFilter(
        launchesList: List<Launch>,
        filterYears: List<String>,
        organizeDescending: Boolean
    ): List<Launch> {
        val filterLaunches = launchesList
            .filter { launch ->
                when {
                    filterYears.isEmpty() -> true
                    else -> filterYears.contains(launch.launchYear) && launch.launchSuccess
                }
            }
            .let { filterList ->
                if (organizeDescending) {
                    filterList.sortedByDescending { it.launchYear.toInt() }
                } else {
                    filterList
                }
            }
        return filterLaunches
    }
}
