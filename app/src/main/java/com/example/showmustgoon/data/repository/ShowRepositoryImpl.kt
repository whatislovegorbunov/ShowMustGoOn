package com.example.showmustgoon.data.repository

import com.example.showmustgoon.domain.model.Show
import com.example.showmustgoon.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
) : ShowRepository {

    override fun observeShows(): Flow<List<Show>> = flowOf(emptyList())

    override suspend fun getShow(id: String): Result<Show> =
        Result.failure(NotImplementedError("Show retrieval not yet implemented"))
}
