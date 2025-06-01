package com.telda.teldamovies.core.data.repository

import com.telda.teldamovies.core.data.api.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi) {
    suspend fun getPopularMovies(language: String, page: Int) = api.getPopularMovies(language, page)
    suspend fun searchMovies(query: String) = api.searchMovies(query)
    suspend fun getMovieDetails(movieId: Int) = api.getMovieDetails(movieId)
    suspend fun getSimilarMovies(movieId: Int) = api.getSimilarMovies(movieId)
    suspend fun getMovieCredits(movieId: Int) = api.getMovieCredits(movieId)
}