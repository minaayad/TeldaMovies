package com.telda.teldamovies.core.data.api

import com.telda.teldamovies.core.data.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): PopularMoviesResponse

//    @GET("search/movie")
//    suspend fun searchMovies(
//        @Query("query") query: String
//    ): MovieResponse
//
//    @GET("movie/{movie_id}")
//    suspend fun getMovieDetails(
//        @Path("movie_id") movieId: Int,
//    ): MovieDetails
//
//    @GET("movie/{movie_id}/similar")
//    suspend fun getSimilarMovies(
//        @Path("movie_id") movieId: Int,
//    ): MovieResponse
//
//    @GET("movie/{movie_id}/credits")
//    suspend fun getMovieCredits(
//        @Path("movie_id") movieId: Int,
//    ): CreditsResponse

}
