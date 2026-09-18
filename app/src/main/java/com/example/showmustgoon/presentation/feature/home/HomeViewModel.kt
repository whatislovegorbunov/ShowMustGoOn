package com.example.showmustgoon.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showmustgoon.domain.usecase.GetShowsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    getShowsUseCase: GetShowsUseCase
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = getShowsUseCase()
        .map { shows -> HomeUiState(shows = shows, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )
}
