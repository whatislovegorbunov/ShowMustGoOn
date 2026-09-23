package com.example.showmustgoon.data.impl

import app.cash.turbine.test
import com.example.showmustgoon.data.local.NoteDao
import com.example.showmustgoon.data.local.NoteEntity
import com.example.showmustgoon.domain.model.Note
import io.mockk.Runs
import io.mockk.capture
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NoteRepositoryImplTest {

    private val noteDao = mockk<NoteDao>(relaxed = true)
    private val repository = NoteRepositoryImpl(noteDao)

    @Test
    fun observeNotes_initiallyReturnsEmptyList() = runTest {
        every { noteDao.observeAll() } returns flowOf(emptyList())
        repository.observeNotes().test {
            assertEquals(emptyList<Note>(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun addNote_callsDaoInsertAndReturnsSuccess() = runTest {
        coEvery { noteDao.insert(any()) } just Runs
        val result = repository.addNote("Title", "Content")
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { noteDao.insert(any()) }
    }

    @Test
    fun deleteNote_callsDaoDeleteByIdAndReturnsSuccess() = runTest {
        coEvery { noteDao.deleteById(any()) } just Runs
        val result = repository.deleteNote("note-id")
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { noteDao.deleteById("note-id") }
    }

    @Test
    fun observeNotes_mapsEntityToDomainCorrectly() = runTest {
        val entity = NoteEntity("1", "Title", "Content", 1000L)
        every { noteDao.observeAll() } returns flowOf(listOf(entity))
        repository.observeNotes().test {
            val notes = awaitItem()
            assertEquals("1", notes[0].id)
            assertEquals("Title", notes[0].title)
            assertEquals("Content", notes[0].content)
            assertEquals(1000L, notes[0].createdAt)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun addNote_generatesUniqueIdsForDifferentNotes() = runTest {
        val capturedEntities = mutableListOf<NoteEntity>()
        coEvery { noteDao.insert(capture(capturedEntities)) } just Runs

        repository.addNote("First", "Content 1")
        repository.addNote("Second", "Content 2")

        coVerify(exactly = 2) { noteDao.insert(any()) }
        assertEquals(2, capturedEntities.size)
        assertNotEquals(capturedEntities[0].id, capturedEntities[1].id)
    }

    @Test
    fun addNote_correctlySavesTitleAndContentFields() = runTest {
        val entitySlot = slot<NoteEntity>()
        coEvery { noteDao.insert(capture(entitySlot)) } just Runs

        repository.addNote("My Title", "My Content")

        assertEquals("My Title", entitySlot.captured.title)
        assertEquals("My Content", entitySlot.captured.content)
    }

    @Test
    fun deleteNote_withSpecificId_passesIdToDao() = runTest {
        coEvery { noteDao.deleteById(any()) } just Runs

        repository.deleteNote("specific-id")

        coVerify(exactly = 1) { noteDao.deleteById("specific-id") }
    }

    @Test
    fun addNote_whenDaoThrows_returnsFailure() = runTest {
        coEvery { noteDao.insert(any()) } throws RuntimeException("DB error")

        val result = repository.addNote("Title", "Content")

        assertTrue(result.isFailure)
        assertEquals("DB error", result.exceptionOrNull()?.message)
    }

    @Test
    fun deleteNote_whenDaoThrows_returnsFailure() = runTest {
        coEvery { noteDao.deleteById(any()) } throws RuntimeException("DB error")

        val result = repository.deleteNote("note-id")

        assertTrue(result.isFailure)
        assertEquals("DB error", result.exceptionOrNull()?.message)
    }
}
