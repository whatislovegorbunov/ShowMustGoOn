package com.example.showmustgoon.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showmustgoon.data.api.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    noteRepository: NoteRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = noteRepository.observeNotes()
        .map { notes -> HomeUiState(notes = notes, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )
}
