package com.telda.teldamovies.core.domain.ListMovies.usecase

import com.telda.teldamovies.core.data.local.WatchlistDao
import com.telda.teldamovies.core.data.local.WatchlistEntity
import javax.inject.Inject

class GetWatchList @Inject constructor(
    private val watchlistDao: WatchlistDao
) {
    suspend operator fun invoke(): List<WatchlistEntity> {
        return watchlistDao.getAll()
    }
} 