package com.example.showmustgoon.data.api

import com.example.showmustgoon.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun addNote(title: String, content: String): Result<Unit>
    suspend fun deleteNote(id: String): Result<Unit>
}
