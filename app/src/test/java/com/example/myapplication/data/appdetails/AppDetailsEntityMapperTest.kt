package com.example.myapplication.data.appdetails

import com.example.myapplication.domain.appdetails.AppDetails
import com.example.myapplication.domain.appdetails.Category
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AppDetailsEntityMapperTest {

    private lateinit var mapper: AppDetailsEntityMapper

    @Before
    fun setUp() {
        mapper = AppDetailsEntityMapper()
    }

    @Test
    fun `toEntity converts domain to entity correctly`() {
        val domain = AppDetails(
            id = "id1",
            name = "App",
            developer = "Dev",
            category = Category.GAME,
            ageRating = 12,
            size = 45.5f,
            iconUrl = "icon.png",
            screenshotUrlList = listOf("scr1", "scr2"),
            description = "desc",
            isInWishlist = true
        )

        val entity = mapper.toEntity(domain)

        assertEquals("id1", entity.id)
        assertEquals("App", entity.name)
        assertEquals("Dev", entity.developer)
        assertEquals(Category.GAME, entity.category)
        assertEquals(12, entity.ageRating)
        assertEquals(45.5f, entity.size, 0.01f)
        assertEquals("icon.png", entity.iconUrl)
        assertEquals("scr1,scr2", entity.screenshots)
        assertEquals("desc", entity.description)
        assertTrue(entity.isInWishlist)
    }

    @Test
    fun `toEntity handles null screenshotUrlList`() {
        val domain = AppDetails(
            id = "id2",
            name = "No Screenshots",
            developer = "Dev",
            category = Category.UTILITIES,
            ageRating = 0,
            size = 10f,
            iconUrl = "ic",
            screenshotUrlList = null,
            description = "desc"
        )

        val entity = mapper.toEntity(domain)

        assertNull(entity.screenshots)
    }

    @Test
    fun `toDomain converts entity to domain correctly`() {
        val entity = AppDetailsEntity(
            id = "ent1",
            name = "EntityApp",
            developer = "EntDev",
            category = Category.EDUCATION,
            ageRating = 7,
            size = 22.2f,
            iconUrl = "ent_icon",
            screenshots = "a,b,c",
            description = "ent desc",
            isInWishlist = false
        )

        val domain = mapper.toDomain(entity)

        assertEquals("ent1", domain.id)
        assertEquals("EntityApp", domain.name)
        assertEquals("EntDev", domain.developer)
        assertEquals(Category.EDUCATION, domain.category)
        assertEquals(7, domain.ageRating)
        assertEquals(22.2f, domain.size, 0.01f)
        assertEquals("ent_icon", domain.iconUrl)
        assertEquals(listOf("a", "b", "c"), domain.screenshotUrlList)
        assertEquals("ent desc", domain.description)
        assertFalse(domain.isInWishlist)
    }

    @Test
    fun `toDomain handles empty screenshots string as empty list`() {
        val entity = AppDetailsEntity(
            id = "ent2",
            name = "EmptyScr",
            developer = "D",
            category = Category.OTHER,
            ageRating = 0,
            size = 0f,
            iconUrl = "",
            screenshots = "",
            description = ""
        )

        val domain = mapper.toDomain(entity)

        assertEquals(emptyList<String>(), domain.screenshotUrlList)
    }

    @Test
    fun `toDomain handles null screenshots`() {
        val entity = AppDetailsEntity(
            id = "ent3",
            name = "NullScr",
            developer = "D",
            category = Category.BUSINESS,
            ageRating = 18,
            size = 5f,
            iconUrl = "i",
            screenshots = null,
            description = "d"
        )

        val domain = mapper.toDomain(entity)

        assertNull(domain.screenshotUrlList)
    }
}