package com.example.showmustgoon.presentation.feature.home

import com.example.showmustgoon.domain.model.Note

data class HomeUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
