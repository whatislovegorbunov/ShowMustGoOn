package com.example.showmustgoon.domain.usecase

import app.cash.turbine.test
import com.example.showmustgoon.domain.model.Show
import com.example.showmustgoon.domain.model.ShowStatus
import com.example.showmustgoon.domain.repository.ShowRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetShowsUseCaseTest {

    private val repository = mockk<ShowRepository>()
    private val useCase = GetShowsUseCase(repository)

    @Test
    fun `invoke returns shows from repository`() = runTest {
        val shows = listOf(
            Show("1", "Hamlet", "Tragedy", ShowStatus.UPCOMING),
            Show("2", "Macbeth", "Drama", ShowStatus.LIVE)
        )
        every { repository.observeShows() } returns flowOf(shows)

        useCase().test {
            assertEquals(shows, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns empty list when repository has no shows`() = runTest {
        every { repository.observeShows() } returns flowOf(emptyList())

        useCase().test {
            assertEquals(emptyList<Show>(), awaitItem())
            awaitComplete()
        }
    }
}
