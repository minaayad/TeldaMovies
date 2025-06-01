package com.telda.teldamovies.core.domain.MovieDetails.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telda.teldamovies.core.data.model.Cast
import com.telda.teldamovies.core.data.model.Crew
import com.telda.teldamovies.core.data.model.Movie
import com.telda.teldamovies.core.data.model.MovieDetails
import com.telda.teldamovies.core.domain.MovieDetails.usecase.GetMovieCredits
import com.telda.teldamovies.core.domain.MovieDetails.usecase.GetMovieDetails
import com.telda.teldamovies.core.domain.MovieDetails.usecase.GetSimilarMovies
import com.telda.teldamovies.core.domain.MovieDetails.usecase.AddToWatchList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetails,
    private val getSimilarMoviesUseCase: GetSimilarMovies,
    private val getMovieCreditsUseCase: GetMovieCredits,
    private val addToWatchListUseCase: AddToWatchList
) : ViewModel() {

    var movieDetails by mutableStateOf<MovieDetails?>(null)
        private set

    var similarMovies by mutableStateOf<List<Movie>>(emptyList())
        private set

    var topActors by mutableStateOf<List<Cast>>(emptyList())
        private set

    var topDirectors by mutableStateOf<List<Crew>>(emptyList())
        private set

    fun loadMovieData(movieId: Int) {
        viewModelScope.launch {
            val details = getMovieDetailsUseCase(movieId)
            val similars = getSimilarMoviesUseCase(movieId).results.take(5)
            movieDetails = details
            similarMovies = similars

            val allCredits = similars.map { getMovieCreditsUseCase(it.id) }

            val allActors = allCredits.flatMap { it.cast }
                .filter { it.knownForDepartment == "Acting" }
                .distinctBy { it.id }
                .sortedByDescending { it.popularity }
                .take(5)

            val allDirectors = allCredits.flatMap { it.crew }
                .filter { it.knownForDepartment == "Directing" }
                .distinctBy { it.id }
                .sortedByDescending { it.popularity }
                .take(5)

            topActors = allActors
            topDirectors = allDirectors
        }
    }

    fun addToWatchList() {
        val movie = movieDetails ?: return
        viewModelScope.launch {
            addToWatchListUseCase(movie)
        }
    }
}