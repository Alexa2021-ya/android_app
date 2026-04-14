package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.mapToDomain
import com.example.myapplication.data.mapper.mapToDomainList
import com.example.myapplication.network.api.ApiService
import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AppRepository {

    override suspend fun getAppList(): List<AppItem> = withContext(Dispatchers.IO) {
        val response = apiService.getCatalog()
        return@withContext response.mapToDomainList()
    }

    override suspend fun getAppById(id: String): AppItem? = withContext(Dispatchers.IO) {
        return@withContext try {
            val details = apiService.getAppDetails(id)
            details.mapToDomain()
        } catch (e: Exception) {
            null
        }
    }

    override fun getAppListFlow(): Flow<List<AppItem>> = flow {
        val list = withContext(Dispatchers.IO) { getAppList() }
        emit(list)
    }
}