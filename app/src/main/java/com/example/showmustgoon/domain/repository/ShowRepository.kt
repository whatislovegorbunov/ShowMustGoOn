package com.example.showmustgoon.domain.repository

import com.example.showmustgoon.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface ShowRepository {
    fun observeShows(): Flow<List<Show>>
    suspend fun getShow(id: String): Result<Show>
}
