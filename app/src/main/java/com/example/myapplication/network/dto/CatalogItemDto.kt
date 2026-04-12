package com.example.myapplication.network.dto

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
    val screenshotUrls: List<String>? = null,
    @SerializedName("developer")
    val developer: String? = null,
    @SerializedName("ageRating")
    val ageRating: Int? = null,
    @SerializedName("size")
    val size: Float? = null
)