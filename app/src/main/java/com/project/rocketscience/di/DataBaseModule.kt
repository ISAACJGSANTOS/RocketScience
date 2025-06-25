package com.project.rocketscience.di

import android.content.Context
import androidx.room.Room
import com.project.rocketscience.data.local.database.AppDataBase
import com.project.rocketscience.data.local.database.dao.CompanyInfoDao
import com.project.rocketscience.data.local.database.dao.LaunchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Module that provides Room database and DAO instances.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    /**
     * Provides a singleton instance of the [AppDataBase] (Room database).
     *
     * @param context The application context, injected by Hilt via [@ApplicationContext].
     * @return The singleton [AppDataBase] instance.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "spacex_db"
        ).build()
    }


    /**
     * Provides a singleton instance of the [CompanyInfoDao].
     *
     * This DAO is obtained from the provided [AppDataBase] instance.
     *
     * @param appDataBase The singleton [AppDataBase] instance, injected by Hilt.
     * @return The singleton [CompanyInfoDao] instance.
     */
    @Provides
    @Singleton
    fun provideCompanyInfoDao(appDataBase: AppDataBase): CompanyInfoDao {
        return appDataBase.getCompanyInfo()
    }

    /**
     * Provides a singleton instance of the [LaunchDao].
     *
     * This DAO is obtained from the provided [AppDataBase] instance.
     *
     * @param appDataBase The singleton [AppDataBase] instance, injected by Hilt.
     * @return The singleton [LaunchDao] instance.
     */
    @Provides
    @Singleton
    fun provideLaunchesDao(appDataBase: AppDataBase): LaunchDao {
        return appDataBase.getLaunches()
    }
}
