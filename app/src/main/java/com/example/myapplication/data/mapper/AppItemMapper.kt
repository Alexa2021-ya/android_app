package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.AppItemDto
import com.example.myapplication.domain.model.AppItem

object AppItemMapper {
    fun mapToDomain(dto: AppItemDto): AppItem {
        return AppItem(
            id = dto.id,
            icon = dto.icon,
            title = dto.title,
            description = dto.description,
            category = dto.category,
            screenshotUrls = dto.screenshotUrls
        )
    }

    fun mapToDomainList(dtoList: List<AppItemDto>): List<AppItem> {
        return dtoList.map { mapToDomain(it) }
    }
}