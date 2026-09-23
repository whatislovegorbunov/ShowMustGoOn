package com.example.showmustgoon.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showmustgoon.data.api.NoteRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = noteRepository.observeNotes()
        .map { notes -> HomeUiState(notes = notes, isLoading = false) }
        .catch { throwable -> emit(HomeUiState(isLoading = false, error = throwable.message)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )

    fun deleteNote(id: String) {
        viewModelScope.launch {
            noteRepository.deleteNote(id)
                .onFailure { throwable ->
                    _deleteError.emit(throwable.message)
                }
        }
    }

    private val _deleteError = MutableSharedFlow<String>()
    val deleteError: SharedFlow<String> = _deleteError.asSharedFlow()
}
