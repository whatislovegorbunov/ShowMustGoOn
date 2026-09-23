package com.example.showmustgoon.data.local

import com.example.showmustgoon.domain.model.Note

fun NoteEntity.toDomain(): Note = Note(id = id, title = title, content = content, createdAt = createdAt)

fun Note.toEntity(): NoteEntity = NoteEntity(id = id, title = title, content = content, createdAt = createdAt)
