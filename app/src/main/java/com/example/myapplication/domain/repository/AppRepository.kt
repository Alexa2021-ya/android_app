package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.AppItem
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getAppList(): List<AppItem>
    suspend fun getAppById(id: String): AppItem?
    fun getAppListFlow(): Flow<List<AppItem>>
}