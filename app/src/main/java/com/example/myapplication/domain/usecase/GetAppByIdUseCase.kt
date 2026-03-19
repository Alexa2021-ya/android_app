package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.repository.AppRepository

class GetAppByIdUseCase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: String): AppItem? {
        return repository.getAppById(id)
    }
}