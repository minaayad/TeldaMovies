package com.telda.teldamovies.core.domain.MovieDetails.usecase

import com.telda.teldamovies.core.data.model.MoviesResponse
import com.telda.teldamovies.core.data.repository.MovieRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MoviesResponse {
        return repository.getSimilarMovies(movieId)
    }
} 