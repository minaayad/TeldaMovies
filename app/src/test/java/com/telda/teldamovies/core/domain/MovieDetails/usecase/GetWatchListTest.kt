package com.telda.teldamovies.core.domain.MovieDetails.usecase

import com.telda.teldamovies.core.data.local.WatchlistDao
import com.telda.teldamovies.core.data.local.WatchlistEntity
import com.telda.teldamovies.core.domain.ListMovies.usecase.GetWatchList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetWatchListTest {
    private lateinit var dao: WatchlistDao
    private lateinit var useCase: GetWatchList

    @Before
    fun setUp() {
        dao = mock(WatchlistDao::class.java)
        useCase = GetWatchList(dao)
    }

    @Test
    fun `invoke should return watchlist`() = runBlocking {
        val expected = listOf(WatchlistEntity(1, "Movie"))
        `when`(dao.getAll()).thenReturn(expected)
        val result = useCase()
        assertEquals(expected, result)
    }
} 