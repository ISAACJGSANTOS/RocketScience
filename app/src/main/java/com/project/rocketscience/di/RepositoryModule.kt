package com.project.rocketscience.di

import com.project.rocketscience.data.local.LocalDataSource
import com.project.rocketscience.data.local.LocalDataSourceImpl
import com.project.rocketscience.data.remote.RemoteDataSource
import com.project.rocketscience.data.remote.RemoteDataSourceImpl
import com.project.rocketscience.data.repository.AppRepository
import com.project.rocketscience.data.repository.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides bindings for repository and data source interfaces.
 *
 * This module binds the concrete implementations to their corresponding interfaces,
 * allowing Hilt to inject the correct dependencies wherever they are required.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds [AppRepositoryImpl] to [AppRepository].
     *
     * @param appRepositoryImpl The implementation of [AppRepository].
     * @return The bound [AppRepository] instance.
     */
    @Binds
    @Singleton
    abstract fun bindAppRepository(
        appRepositoryImpl: AppRepositoryImpl
    ): AppRepository

    /**
     * Binds [RemoteDataSourceImpl] to [RemoteDataSource].
     *
     * @param remoteDataSourceImpl The implementation of [RemoteDataSource].
     * @return The bound [RemoteDataSource] instance.
     */
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    /**
     * Binds [LocalDataSourceImpl] to [LocalDataSource].
     *
     * @param localDataSourceImpl The implementation of [LocalDataSource].
     * @return The bound [LocalDataSource] instance.
     */
    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource
}
