package com.example.showmustgoon.presentation.feature.home

import app.cash.turbine.test
import com.example.showmustgoon.domain.model.Show
import com.example.showmustgoon.domain.model.ShowStatus
import com.example.showmustgoon.domain.repository.ShowRepository
import com.example.showmustgoon.domain.usecase.GetShowsUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository = mockk<ShowRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() = runTest {
        every { repository.observeShows() } returns flowOf(emptyList())
        val viewModel = HomeViewModel(GetShowsUseCase(repository))

        viewModel.uiState.test {
            val initial = awaitItem()
            assertTrue(initial.isLoading)
            assertTrue(initial.shows.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state updates with shows after loading`() = runTest {
        val shows = listOf(
            Show("1", "Hamlet", "Tragedy", ShowStatus.UPCOMING),
            Show("2", "Macbeth", "Drama", ShowStatus.LIVE)
        )
        every { repository.observeShows() } returns flowOf(shows)
        val viewModel = HomeViewModel(GetShowsUseCase(repository))

        viewModel.uiState.test {
            assertTrue(awaitItem().isLoading)
            advanceUntilIdle()
            val loaded = awaitItem()
            assertFalse(loaded.isLoading)
            assertEquals(shows, loaded.shows)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state shows empty list and not loading when repository returns empty`() = runTest {
        every { repository.observeShows() } returns flowOf(emptyList())
        val viewModel = HomeViewModel(GetShowsUseCase(repository))

        viewModel.uiState.test {
            assertTrue(awaitItem().isLoading)
            advanceUntilIdle()
            val loaded = awaitItem()
            assertFalse(loaded.isLoading)
            assertTrue(loaded.shows.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
