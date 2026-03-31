package com.example.myapplication.presentation.di

import com.example.myapplication.data.repository.AppRepositoryImpl
import com.example.myapplication.domain.repository.AppDetailsRepository
import com.example.myapplication.data.repository.AppDetailsRepositoryImpl
import com.example.myapplication.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppRepository(impl: AppRepositoryImpl): AppRepository

    @Binds
    @Singleton
    abstract fun bindAppDetailsRepository(impl: AppDetailsRepositoryImpl): AppDetailsRepository
}
