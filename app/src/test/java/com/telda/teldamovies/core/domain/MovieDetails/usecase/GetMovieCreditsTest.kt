package com.telda.teldamovies.core.domain.MovieDetails.usecase

import com.telda.teldamovies.core.data.model.Credits
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


class GetMovieCreditsTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: GetMovieCredits

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetMovieCredits(repository)
    }

    @Test
    fun `invoke should return movie credits`() = runTest {
        val expected = mockk<Credits>()
        coEvery { repository.getMovieCredits(1) } returns expected

        val result = useCase(1)

        assertEquals(expected, result)
        coVerify { repository.getMovieCredits(1) }
    }
}