package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.MockDataSource
import com.example.myapplication.data.mapper.mapToDomain
import com.example.myapplication.data.mapper.mapToDomainList
import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor() : AppRepository {
    override suspend fun getAppList(): List<AppItem> {
        delay(500)
        return MockDataSource.appListDto.mapToDomainList()
    }

    override suspend fun getAppById(id: String): AppItem? {
        return MockDataSource.appListDto
            .find { it.id == id }
            ?.mapToDomain()
    }

    override fun getAppListFlow(): Flow<List<AppItem>> = flow {
        emit(MockDataSource.appListDto.mapToDomainList())
    }
}