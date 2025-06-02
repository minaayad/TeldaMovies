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

class GetPopularMoviesTest {
    private lateinit var repository: MovieRepository
    private lateinit var useCase: GetPopularMovies

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetPopularMovies(repository)
    }

    @Test
    fun `invoke should return popular movies`() = runTest {
        val expected = MoviesResponse(1, emptyList())
        coEvery { repository.getPopularMovies("en-US", 1) } returns expected

        val result = useCase("en-US", 1)

        assertEquals(expected, result)
        coVerify { repository.getPopularMovies("en-US", 1) }
    }
} 