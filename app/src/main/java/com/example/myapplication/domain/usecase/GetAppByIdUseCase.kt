package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.repository.AppRepository
import javax.inject.Inject

class GetAppByIdUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: String): AppItem? {
        return repository.getAppById(id)
    }
}