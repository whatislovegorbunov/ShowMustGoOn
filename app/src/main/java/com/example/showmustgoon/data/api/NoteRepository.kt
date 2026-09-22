package com.example.showmustgoon.data.api

import com.example.showmustgoon.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNotes(): Flow<List<Show>>
    suspend fun addNote(title: String, content: String): Result<Unit>
}
