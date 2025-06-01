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
import com.telda.teldamovies.core.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
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
            val details = repository.getMovieDetails(movieId)
            val similars = repository.getSimilarMovies(movieId).results.take(5)
            movieDetails = details
            similarMovies = similars

            val allCredits = similars.map { repository.getMovieCredits(it.id) }

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
}