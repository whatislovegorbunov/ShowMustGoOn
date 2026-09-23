package com.example.showmustgoon.presentation.feature.addnote

data class AddNoteUiState(
    val title: String = "",
    val content: String = "",
    val isSaving: Boolean = false,
    val error: String? = null,
    val isSaved: Boolean = false
)
