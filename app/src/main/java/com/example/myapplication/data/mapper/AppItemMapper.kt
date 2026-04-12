package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.AppItemDto
import com.example.myapplication.domain.model.AppItem

fun AppItemDto.mapToDomain(): AppItem {
    return AppItem(
        id = id,
        icon = icon,
        title = title,
        description = description,
        category = category,
        screenshotUrls = screenshotUrls
    )
}

fun List<AppItemDto>.mapToDomainList(): List<AppItem> = map { it.mapToDomain() }