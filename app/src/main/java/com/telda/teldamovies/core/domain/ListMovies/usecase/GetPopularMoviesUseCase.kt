package com.telda.teldamovies.core.domain.ListMovies.usecase

import com.telda.teldamovies.core.data.model.MoviesResponse
import com.telda.teldamovies.core.data.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(language: String = "en-US", page: Int = 1): MoviesResponse {
        return repository.getPopularMovies(language, page)
    }
} 