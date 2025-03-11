package com.example.neuroshield_app.di

import com.example.neuroshield_app.data.services.UserApiService
import com.example.neuroshield_app.data.source.UserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserApiService(): UserApiService {
        return UserApiService() // Ensure UserApiService has a no-arg constructor or use a Factory
    }

    @Provides
    @Singleton
    fun provideUserDataSource(userApiService: UserApiService): UserDataSource {
        return UserDataSource(userApiService)
    }
}

