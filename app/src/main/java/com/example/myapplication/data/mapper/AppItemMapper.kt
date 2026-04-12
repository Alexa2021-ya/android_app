package com.example.myapplication.data.mapper

import com.example.myapplication.data.network.dto.CatalogItemDto
import com.example.myapplication.domain.model.AppItem

fun CatalogItemDto.mapToDomain(): AppItem {
    return AppItem(
        id = id,
        icon = iconUrl,
        title = name,
        description = description,
        category = category,
        screenshotUrls = screenshotUrls ?: emptyList()
    )
}

fun List<CatalogItemDto>.mapToDomainList(): List<AppItem> = map { it.mapToDomain() }