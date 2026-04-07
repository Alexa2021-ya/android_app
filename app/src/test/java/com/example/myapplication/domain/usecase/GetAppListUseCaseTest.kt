package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.repository.AppRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAppListUseCaseTest {

    private lateinit var repository: AppRepository
    private lateinit var useCase: GetAppListUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetAppListUseCase(repository)
    }

    @Test
    fun `invoke returns app list from repository`() = runTest {
        val list = listOf(mockk<AppItem>(), mockk<AppItem>())
        coEvery { repository.getAppList() } returns list

        val result = useCase.invoke()

        assertEquals(list, result)
        coVerify { repository.getAppList() }
    }

    @Test
    fun `invoke returns empty list when repository returns empty`() = runTest {
        coEvery { repository.getAppList() } returns emptyList()

        val result = useCase.invoke()

        assertEquals(emptyList<AppItem>(), result)
    }

    @Test
    fun `invokeFlow returns flow from repository`() = runTest {
        val flow = flowOf(listOf(mockk<AppItem>()))
        coEvery { repository.getAppListFlow() } returns flow

        val result = useCase.invokeFlow()

        assertEquals(flow, result)
    }

    @Test
    fun `invokeFlow propagates repository exceptions`() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { repository.getAppListFlow() } throws exception

        try {
            useCase.invokeFlow()
        } catch (e: RuntimeException) {
            assertEquals("Network error", e.message)
        }
    }

    @Test
    fun `invoke returns correct list with real data`() = runTest {
        val expected = listOf(
            AppItem("1", "ic1", "Title1", "Desc1", "Cat1", emptyList()),
            AppItem("2", "ic2", "Title2", "Desc2", "Cat2", emptyList())
        )
        coEvery { repository.getAppList() } returns expected

        val actual = useCase.invoke()

        assertEquals(expected, actual)
    }
}