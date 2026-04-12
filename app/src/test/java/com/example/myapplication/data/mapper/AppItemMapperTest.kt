package com.example.myapplication.data.mapper

import com.example.myapplication.network.dto.CatalogItemDto
import org.junit.Assert.assertEquals
import org.junit.Test

class AppItemMapperTest {

    @Test
    fun `mapToDomain converts DTO to AppItem correctly`() {
        val dto = CatalogItemDto(
            id = "app123",
            name = "Test App",
            description = "Test description",
            category = "Games",
            iconUrl = "https://example.com/icon.png",
            screenshotUrls = listOf("shot1.jpg", "shot2.jpg")
        )

        val result = dto.mapToDomain()

        assertEquals("app123", result.id)
        assertEquals("Test App", result.title)
        assertEquals("Test description", result.description)
        assertEquals("Games", result.category)
        assertEquals("https://example.com/icon.png", result.icon)
        assertEquals(listOf("shot1.jpg", "shot2.jpg"), result.screenshotUrls)
    }

    @Test
    fun `mapToDomain handles null screenshotUrls as empty list`() {
        val dto = CatalogItemDto(
            id = "app456",
            name = "No Screenshots",
            description = "Desc",
            category = "Tools",
            iconUrl = "icon.png",
            screenshotUrls = null
        )

        val result = dto.mapToDomain()

        assert(result.screenshotUrls.isEmpty())
    }

    @Test
    fun `mapToDomainList converts list of DTOs`() {
        val dtoList = listOf(
            CatalogItemDto("1", "App1", "Desc1", "Cat1", "icon1", null),
            CatalogItemDto("2", "App2", "Desc2", "Cat2", "icon2", emptyList())
        )

        val result = dtoList.mapToDomainList()

        assertEquals(2, result.size)
        assertEquals("App1", result[0].title)
        assertEquals("App2", result[1].title)
    }

    @Test
    fun `mapToDomainList returns empty list for empty input`() {
        val result = emptyList<CatalogItemDto>().mapToDomainList()
        assert(result.isEmpty())
    }

    @Test
    fun `mapToDomain preserves all fields including optional ones`() {
        val dto = CatalogItemDto(
            id = "x",
            name = "Y",
            description = "Z",
            category = "Finance",
            iconUrl = "http://i.com",
            screenshotUrls = listOf("s1")
        )

        val domain = dto.mapToDomain()

        assertEquals("x", domain.id)
        assertEquals("Y", domain.title)
        assertEquals("Z", domain.description)
        assertEquals("Finance", domain.category)
        assertEquals("http://i.com", domain.icon)
        assertEquals(listOf("s1"), domain.screenshotUrls)
    }
}