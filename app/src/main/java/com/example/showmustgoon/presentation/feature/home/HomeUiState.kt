package com.example.showmustgoon.presentation.feature.home

import com.example.showmustgoon.domain.model.Show

data class HomeUiState(
    val shows: List<Show> = emptyList(),
    val isLoading: Boolean = true
)
