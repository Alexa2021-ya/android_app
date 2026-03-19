package com.example.myapplication.presentation.di

import com.example.myapplication.data.repository.AppRepositoryImpl
import com.example.myapplication.domain.repository.AppRepository
import com.example.myapplication.domain.usecase.GetAppByIdUseCase
import com.example.myapplication.domain.usecase.GetAppListUseCase

object DependencyContainer {
    private val appRepository: AppRepository by lazy {
        AppRepositoryImpl()
    }

    val getAppListUseCase: GetAppListUseCase by lazy {
        GetAppListUseCase(appRepository)
    }

    val getAppByIdUseCase: GetAppByIdUseCase by lazy {
        GetAppByIdUseCase(appRepository)
    }
}