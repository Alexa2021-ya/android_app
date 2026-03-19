package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.MockDataSource
import com.example.myapplication.data.mapper.AppItemMapper
import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepositoryImpl : AppRepository {
    override suspend fun getAppList(): List<AppItem> {
        delay(500) // симуляция загрузки
        return AppItemMapper.mapToDomainList(MockDataSource.appListDto)
    }

    override suspend fun getAppById(id: String): AppItem? {
        return MockDataSource.appListDto
            .find { it.id == id }
            ?.let { AppItemMapper.mapToDomain(it) }
    }

    override fun getAppListFlow(): Flow<List<AppItem>> = flow {
        emit(AppItemMapper.mapToDomainList(MockDataSource.appListDto))
    }
}