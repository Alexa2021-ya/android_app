package com.example.myapplication.data.repository

import com.example.myapplication.data.appdetails.AppDetailsDao
import com.example.myapplication.data.appdetails.AppDetailsEntity
import com.example.myapplication.data.appdetails.AppDetailsEntityMapper
import com.example.myapplication.domain.appdetails.AppDetails
import com.example.myapplication.domain.appdetails.Category
import com.example.myapplication.network.api.ApiService
import com.example.myapplication.network.dto.CatalogItemDto
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AppDetailsRepositoryImplTest {

    private lateinit var dao: AppDetailsDao
    private lateinit var apiService: ApiService
    private lateinit var mapper: AppDetailsEntityMapper
    private lateinit var repository: AppDetailsRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        apiService = mockk()
        mapper = mockk()
        repository = AppDetailsRepositoryImpl(dao, apiService, mapper)
    }

    @Test
    fun `getAppDetails returns cached entity when present`() = runTest {
        val entity = mockk<AppDetailsEntity>()
        val domain = mockk<AppDetails>()
        coEvery { dao.getAppDetails("123") } returns flowOf(entity)
        every { mapper.toDomain(entity) } returns domain

        val result = repository.getAppDetails("123")

        assertEquals(domain, result)
        coVerify(exactly = 0) { apiService.getAppDetails(any()) }
    }

    @Test
    fun `getAppDetails fetches from network when cache missing`() = runTest {
        coEvery { dao.getAppDetails("456") } returns flowOf(null)
        val dto = CatalogItemDto("456", "NetApp", "desc", "Games", "ic", null)
        coEvery { apiService.getAppDetails("456") } returns dto
        val domain = mockk<AppDetails>()
        every { mapper.toDomain(any()) } returns domain
        coEvery { dao.insertAppDetails(any()) } just runs

        val result = repository.getAppDetails("456")

        assertEquals(domain, result)
        coVerify { apiService.getAppDetails("456") }
        coVerify { dao.insertAppDetails(any()) }
    }

    @Test
    fun `getAppDetails returns null on network error`() = runTest {
        coEvery { dao.getAppDetails("789") } returns flowOf(null)
        coEvery { apiService.getAppDetails("789") } throws Exception("Timeout")

        val result = repository.getAppDetails("789")

        assertNull(result)
    }

    @Test
    fun `observeAppDetails emits from cache then updates`() = runTest {
        val entity = AppDetailsEntity(
            id = "obs1", name = "Obs", developer = "Dev", category = Category.MUSIC,
            ageRating = 0, size = 1f, iconUrl = "", description = "", isInWishlist = false
        )
        val domain = AppDetails(
            id = "obs1", name = "Obs", developer = "Dev", category = Category.MUSIC,
            ageRating = 0, size = 1f, iconUrl = "", screenshotUrlList = null, description = ""
        )
        coEvery { dao.getAppDetails("obs1") } returns flowOf(entity)
        every { mapper.toDomain(entity) } returns domain

        val flow = repository.observeAppDetails("obs1")
        val result = flow.firstOrNull()

        assertEquals(domain, result)
    }

    @Test
    fun `toggleWishlist updates existing entity`() = runTest {
        val entity = AppDetailsEntity(
            id = "w1", name = "Wish", developer = "D", category = Category.APP,
            ageRating = 0, size = 0f, iconUrl = "", description = "", isInWishlist = false
        )
        coEvery { dao.getAppDetails("w1") } returns flowOf(entity)
        coEvery { dao.updateWishlistStatus("w1", true) } just runs

        repository.toggleWishlist("w1")

        coVerify { dao.updateWishlistStatus("w1", true) }
    }

    @Test
    fun `toggleWishlist fetches from network if missing then toggles`() = runTest {
        coEvery { dao.getAppDetails("new") } returns flowOf(null)
        val dto = CatalogItemDto("new", "NewApp", "desc", "Prod", "ic", null)
        coEvery { apiService.getAppDetails("new") } returns dto
        val entity = mockk<AppDetailsEntity>(relaxed = true)
        every { entity.isInWishlist } returns false
        every { mapper.toEntity(any()) } returns entity
        coEvery { dao.insertAppDetails(entity) } just runs
        coEvery { dao.updateWishlistStatus("new", true) } just runs

        repository.toggleWishlist("new")

        coVerify { dao.updateWishlistStatus("new", true) }
    }
}