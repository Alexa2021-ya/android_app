// CatalogItemDto.kt
package com.example.myapplication.data.network.dto

import com.google.gson.annotations.SerializedName

data class CatalogItemDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("iconUrl")
    val iconUrl: String,
    @SerializedName("screenshotUrls")
    val screenshotUrls: List<String>? = null
)