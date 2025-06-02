package com.telda.teldamovies.core.data.local.mapper

import com.telda.teldamovies.core.data.model.MovieDetails
import com.telda.teldamovies.core.data.local.WatchlistEntity

/**
 * Extension function to convert MovieDetails to WatchlistEntity.
 * This is used to add a movie to the watchlist local database.
 */

fun MovieDetails.toWatchlistEntity(): WatchlistEntity {
    return WatchlistEntity(
        movieId = id,
        title = title
    )
}