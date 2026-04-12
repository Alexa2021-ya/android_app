package com.example.myapplication.domain.repository

import com.example.myapplication.domain.appdetails.AppDetails

interface AppDetailsRepository {
    suspend fun getAppDetails(id: String): AppDetails?
}