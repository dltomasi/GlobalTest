package com.global.test.globaltest.injection

import android.content.Context
import android.content.SharedPreferences
import com.global.test.globaltest.network.DataService
import com.global.test.globaltest.network.WebClient
import com.global.test.globaltest.repositories.DataRepository
import com.global.test.globaltest.repositories.DataRepositoryImpl
import com.global.test.globaltest.repositories.LocalRepository
import com.global.test.globaltest.repositories.SharedPreferencesRepository
import com.global.test.globaltest.ui.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(val context: Context) {

    @Provides
    @Singleton
    fun provideShared(): SharedPreferences {
        return context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDataService() : DataService {
        return WebClient().dataService()
    }

    @Provides
    @Singleton
    fun provideDataRepository(dataService: DataService): DataRepository {
        return DataRepositoryImpl(dataService)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(preferences: SharedPreferences): LocalRepository {
        return SharedPreferencesRepository(preferences)
    }

}
