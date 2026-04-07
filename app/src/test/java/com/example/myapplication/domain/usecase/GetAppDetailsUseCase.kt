package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.appdetails.AppDetails
import com.example.myapplication.domain.repository.AppDetailsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetAppDetailsUseCaseTest {

    private lateinit var repository: AppDetailsRepository
    private lateinit var useCase: GetAppDetailsUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetAppDetailsUseCase(repository)
    }

    @Test
    fun `invoke returns app details from repository`() = runTest {
        val details = mockk<AppDetails>()
        coEvery { repository.getAppDetails("id1") } returns details

        val result = useCase.invoke("id1")

        assertEquals(details, result)
        coVerify { repository.getAppDetails("id1") }
    }

    @Test
    fun `invoke returns null when repository returns null`() = runTest {
        coEvery { repository.getAppDetails("missing") } returns null

        val result = useCase.invoke("missing")

        assertNull(result)
    }

    @Test
    fun `observe returns flow from repository`() = runTest {
        val flow = flowOf(mockk<AppDetails>())
        coEvery { repository.observeAppDetails("id2") } returns flow

        val result = useCase.observe("id2")

        assertEquals(flow, result)
    }

    @Test
    fun `toggleWishlist delegates to repository`() = runTest {
        coEvery { repository.toggleWishlist("id3") } returns Unit

        useCase.toggleWishlist("id3")

        coVerify { repository.toggleWishlist("id3") }
    }

    @Test
    fun `observe propagates repository exceptions`() = runTest {
        val exception = RuntimeException("Database error")
        coEvery { repository.observeAppDetails("bad") } throws exception

        try {
            useCase.observe("bad")
        } catch (e: RuntimeException) {
            assertEquals("Database error", e.message)
        }
    }
}