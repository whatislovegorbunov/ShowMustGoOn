package com.example.showmustgoon.domain.usecase

import com.example.showmustgoon.domain.model.Show
import com.example.showmustgoon.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShowsUseCase @Inject constructor(
    private val repository: ShowRepository
) {
    operator fun invoke(): Flow<List<Show>> = repository.observeShows()
}
