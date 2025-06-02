package com.telda.teldamovies.core.domain.ListMovies.usecase

import com.telda.teldamovies.core.data.model.MoviesResponse
import com.telda.teldamovies.core.data.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class SearchMovieTest {
    private lateinit var repository: MovieRepository
    private lateinit var useCase: SearchMovie


    @Before
    fun setUp() {
        repository = mockk()
        useCase = SearchMovie(repository)
    }

    @Test
    fun `invoke should return search results`() = runTest {
        val expected = MoviesResponse(1, emptyList())
        coEvery { repository.searchMovies("query") } returns expected

        val result = useCase("query")

        assertEquals(expected, result)
        coVerify { repository.searchMovies("query") }
    }
}