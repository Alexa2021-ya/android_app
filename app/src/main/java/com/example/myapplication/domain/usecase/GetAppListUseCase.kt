package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppListUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): List<AppItem> {
        return repository.getAppList()
    }

    fun invokeFlow(): Flow<List<AppItem>> {
        return repository.getAppListFlow()
    }
}