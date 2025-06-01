package com.telda.teldamovies.core.domain.MovieDetails.usecase

import com.telda.teldamovies.core.data.model.MovieDetails
import com.telda.teldamovies.core.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetails {
        return repository.getMovieDetails(movieId)
    }
} 