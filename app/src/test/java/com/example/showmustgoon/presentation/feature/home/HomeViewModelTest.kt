package com.example.showmustgoon.presentation.feature.home

import app.cash.turbine.test
import com.example.showmustgoon.domain.model.Show
import com.example.showmustgoon.data.api.NoteRepository
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
    private val repository = mockk<NoteRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_isLoadingWithEmptyNotes() = runTest(testDispatcher) {
        every { repository.observeNotes() } returns flowOf(emptyList())
        val viewModel = HomeViewModel(repository)

        viewModel.uiState.test {
            val initial = awaitItem()
            assertTrue(initial.isLoading)
            assertTrue(initial.notes.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun loadNotes_whenRepositoryReturnsData_emitsSuccessStateWithNotes() = runTest(testDispatcher) {
        val notes = listOf(
            Show("1", "Title 1", "Content 1", 1000L),
            Show("2", "Title 2", "Content 2", 2000L)
        )
        every { repository.observeNotes() } returns flowOf(notes)
        val viewModel = HomeViewModel(repository)

        viewModel.uiState.test {
            assertTrue(awaitItem().isLoading)
            advanceUntilIdle()
            val loaded = awaitItem()
            assertFalse(loaded.isLoading)
            assertEquals(notes, loaded.notes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun loadNotes_whenRepositoryReturnsEmpty_emitsSuccessStateWithEmptyList() = runTest(testDispatcher) {
        every { repository.observeNotes() } returns flowOf(emptyList())
        val viewModel = HomeViewModel(repository)

        viewModel.uiState.test {
            assertTrue(awaitItem().isLoading)
            advanceUntilIdle()
            val loaded = awaitItem()
            assertFalse(loaded.isLoading)
            assertTrue(loaded.notes.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
