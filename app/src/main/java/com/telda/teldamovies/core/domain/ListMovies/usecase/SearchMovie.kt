package com.telda.teldamovies.core.domain.ListMovies.usecase

import com.telda.teldamovies.core.data.model.MoviesResponse
import com.telda.teldamovies.core.data.repository.MovieRepository
import javax.inject.Inject

class SearchMovie @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String): MoviesResponse {
        return repository.searchMovies(query)
    }
} 