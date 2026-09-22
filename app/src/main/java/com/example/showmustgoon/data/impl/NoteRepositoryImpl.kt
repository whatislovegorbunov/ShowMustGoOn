package com.example.showmustgoon.data.impl

import com.example.showmustgoon.data.api.NoteRepository
import com.example.showmustgoon.domain.model.Show
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor() : NoteRepository {

    private val notes = MutableStateFlow<List<Show>>(emptyList())

    override fun observeNotes(): Flow<List<Show>> = notes.asStateFlow()

    override suspend fun addNote(title: String, content: String): Result<Unit> = runCatching {
        val note = Show(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )
        notes.update { current -> listOf(note) + current }
    }
}
