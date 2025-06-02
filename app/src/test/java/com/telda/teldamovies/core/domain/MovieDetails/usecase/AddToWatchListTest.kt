package com.telda.teldamovies.core.domain.MovieDetails.usecase

import com.telda.teldamovies.core.data.local.WatchlistDao
import com.telda.teldamovies.core.data.local.mapper.toWatchlistEntity
import com.telda.teldamovies.core.data.model.MovieDetails
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class AddToWatchListTest {
    private lateinit var dao: WatchlistDao
    private lateinit var useCase: AddToWatchList

    @Before
    fun setUp() {
        dao = mock(WatchlistDao::class.java)
        useCase = AddToWatchList(dao)
    }

    @Test
    fun `invoke should insert movie into watchlist`() = runBlocking {
        val movie = MovieDetails(
            adult = false,
            backdropPath = null,
            belongsToCollection = null,
            budget = 0,
            genres = emptyList(),
            homepage = "",
            id = 1,
            imdbId = null,
            originCountry = emptyList(),
            originalLanguage = "",
            originalTitle = "",
            overview = "",
            popularity = 0.0,
            posterPath = null,
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            releaseDate = "",
            revenue = 0,
            runtime = 0,
            spokenLanguages = emptyList(),
            status = "",
            tagline = null,
            title = "Test Movie",
            video = false,
            voteAverage = 0.0,
            voteCount = 0
        )
        useCase(movie)
        verify(dao).insert(movie.toWatchlistEntity())
    }
}