package com.example.showmustgoon.data.impl

import app.cash.turbine.test
import com.example.showmustgoon.domain.model.Show
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NoteRepositoryImplTest {

    @Test
    fun observeNotes_initiallyReturnsEmptyList() = runTest {
        val repository = NoteRepositoryImpl()

        repository.observeNotes().test {
            assertEquals(emptyList<Show>(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun addNote_addsNoteAndObserveReturnsListWithOneNote() = runTest {
        val repository = NoteRepositoryImpl()

        repository.addNote("Title", "Content")

        repository.observeNotes().test {
            val notes = awaitItem()
            assertEquals(1, notes.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun addNote_generatesUniqueIdsForDifferentNotes() = runTest {
        val repository = NoteRepositoryImpl()

        repository.addNote("Title 1", "Content 1")
        repository.addNote("Title 2", "Content 2")

        repository.observeNotes().test {
            val notes = awaitItem()
            assertEquals(2, notes.size)
            assertNotEquals(notes[0].id, notes[1].id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun addNote_addsNewNoteAtBeginningOfList() = runTest {
        val repository = NoteRepositoryImpl()

        repository.addNote("First", "Content 1")
        repository.addNote("Second", "Content 2")

        repository.observeNotes().test {
            val notes = awaitItem()
            assertEquals("Second", notes[0].title)
            assertEquals("First", notes[1].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun addNote_returnsResultSuccess() = runTest {
        val repository = NoteRepositoryImpl()

        val result = repository.addNote("Title", "Content")

        assertTrue(result.isSuccess)
    }

    @Test
    fun addNote_correctlySavesTitleAndContentFields() = runTest {
        val repository = NoteRepositoryImpl()

        repository.addNote("My Title", "My Content")

        repository.observeNotes().test {
            val note = awaitItem().first()
            assertEquals("My Title", note.title)
            assertEquals("My Content", note.content)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
