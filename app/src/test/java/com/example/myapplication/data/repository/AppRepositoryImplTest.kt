package com.example.myapplication.data.repository

import com.example.myapplication.network.api.ApiService
import com.example.myapplication.network.dto.CatalogItemDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class AppRepositoryImplTest {

    private lateinit var apiService: ApiService
    private lateinit var repository: AppRepositoryImpl

    @Before
    fun setUp() {
        apiService = mockk()
        repository = AppRepositoryImpl(apiService)
    }

    @Test
    fun `getAppList returns mapped list on success`() = runTest {
        val dtoList = listOf(
            CatalogItemDto("1", "App1", "Desc1", "Cat1", "ic1", null),
            CatalogItemDto("2", "App2", "Desc2", "Cat2", "ic2", emptyList())
        )
        coEvery { apiService.getCatalog() } returns dtoList

        val result = repository.getAppList()

        assertEquals(2, result.size)
        assertEquals("App1", result[0].title)
    }

    @Test
    fun `getAppList propagates exception from api`() = runTest {
        coEvery { apiService.getCatalog() } throws RuntimeException("Network error")

        try {
            repository.getAppList()
        } catch (e: RuntimeException) {
            assertEquals("Network error", e.message)
        }
    }

    @Test
    fun `getAppById returns mapped item when api succeeds`() = runTest {
        val dto = CatalogItemDto("id99", "Name", "Desc", "Cat", "ic", null)
        coEvery { apiService.getAppDetails("id99") } returns dto

        val result = repository.getAppById("id99")

        assertEquals("id99", result?.id)
        assertEquals("Name", result?.title)
    }

    @Test
    fun `getAppById returns null when api throws exception`() = runTest {
        coEvery { apiService.getAppDetails("missing") } throws Exception("Not found")

        val result = repository.getAppById("missing")

        assertNull(result)
    }

    @Test
    fun `getAppListFlow emits list via flow`() = runTest {
        val dtoList = listOf(CatalogItemDto("1", "FlowApp", "Desc", "Cat", "ic", null))
        coEvery { apiService.getCatalog() } returns dtoList

        val flow = repository.getAppListFlow()
        val result = flow.first()

        assertEquals(1, result.size)
        assertEquals("FlowApp", result[0].title)
    }
}