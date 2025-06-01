package com.telda.teldamovies.core.domain.MovieDetails.usecase

import com.telda.teldamovies.core.data.model.Credits
import com.telda.teldamovies.core.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Credits
    {
        return repository.getMovieCredits(movieId)
    }
} 