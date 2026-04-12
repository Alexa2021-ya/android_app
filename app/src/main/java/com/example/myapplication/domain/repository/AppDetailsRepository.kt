package com.example.myapplication.domain.repository

import com.example.myapplication.domain.appdetails.AppDetails
import kotlinx.coroutines.flow.Flow

interface AppDetailsRepository {
    suspend fun getAppDetails(id: String): AppDetails?
    suspend fun toggleWishlist(id: String)
    fun observeAppDetails(id: String): Flow<AppDetails>
}