package com.telda.teldamovies.core.domain.MovieDetails.usecase

import com.telda.teldamovies.core.data.local.WatchlistDao
import com.telda.teldamovies.core.data.local.mapper.toWatchlistEntity
import com.telda.teldamovies.core.data.model.MovieDetails
import javax.inject.Inject

class RemoveFromWatchList @Inject constructor(
    private val watchlistDao: WatchlistDao
) {
    suspend operator fun invoke(movie: MovieDetails) {
        watchlistDao.delete(movie.toWatchlistEntity())
    }
} 