package com.example.myapplication.network.api

import com.example.myapplication.network.dto.CatalogItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("catalog")
    suspend fun getCatalog(): List<CatalogItemDto>

    @GET("catalog/{id}")
    suspend fun getAppDetails(@Path("id") id: String): CatalogItemDto
}