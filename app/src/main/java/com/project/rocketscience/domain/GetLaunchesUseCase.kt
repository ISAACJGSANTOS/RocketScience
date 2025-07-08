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
 * A Use Case responsible for retrieving a list of SpaceX launches.
 *
 * This Use Case acts as an intermediary between the presentation layer.
 * and the data layer (represented by [AppRepository]). It encapsulates the specific
 * business logic for fetching launch information.
 *
 * @property appRepository The [AppRepository] from which to fetch the launch data.
 */
class GetLaunchesUseCase @Inject constructor(private val appRepository: AppRepository) {
    /**
     * Executes the Use Case to get a list of SpaceX launches.
     *
     * When invoked, this function calls the [AppRepository.getLaunches] method
     * to fetch the data.
     *
     * @return A [Flow] that emits a [Result] object.
     *         - On success, the [Result] will contain a list of [Launch] objects.
     *         - On failure, the [Result] will contain an [Exception] detailing the error.
     */
    operator fun invoke(): Flow<Result<List<Launch>>> {
        return appRepository.getLaunches()
            .map { Result.success((it)) }
            .catch {
                when (it) {
                    is AppException.CacheMissException -> {
                        emit(Result.failure(it))
                    }

                    else -> {
                        Log.e("GetLaunchesUseCase", "Error getting the launches data: $it")
                    }
                }
            }
    }
}
