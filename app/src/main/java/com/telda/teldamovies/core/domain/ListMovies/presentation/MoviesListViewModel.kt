package com.telda.teldamovies.core.domain.ListMovies.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.telda.teldamovies.core.data.model.Movie
import com.telda.teldamovies.core.domain.ListMovies.usecase.GetPopularMovies
import com.telda.teldamovies.core.domain.ListMovies.usecase.SearchMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.telda.teldamovies.core.data.local.WatchlistEntity
import com.telda.teldamovies.core.domain.ListMovies.usecase.GetWatchList
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMovies,
    private val searchMovieUseCase: SearchMovie,
    private val getWatchListUseCase: GetWatchList
) : ViewModel() {

    var movies by mutableStateOf<List<Movie>>(emptyList())
        private set
    var watchlistIds by mutableStateOf<Set<Int>>(emptySet())
        private set

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    fun loadPopularMovies(language: String = "en-US") {
        if (isLoading || isLastPage) return

        isLoading = true
        viewModelScope.launch {
            try {
                val response = getPopularMoviesUseCase(language, currentPage)
                val newMovies = response.results

                if (newMovies.isEmpty()) {
                    isLastPage = true
                } else {
                    movies = movies + newMovies
                    currentPage++
                }
                updateWatchlistStatus()
            } catch (e: Exception) {
                // Log or handle error
            } finally {
                isLoading = false
            }
        }
    }

    fun resetPagination() {
        currentPage = 1
        isLastPage = false
        movies = emptyList()
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            movies = searchMovieUseCase(query).results
            updateWatchlistStatus()
        }
    }

//    suspend fun isMovieInWatchlist(movieId: Int): Boolean {
//        return isInWatchlistUseCase(movieId)
//    }

    fun updateWatchlistStatus() {
        viewModelScope.launch {
            val watchList = getWatchListUseCase()
            val inWatchlist = movies
                .map { it.id }
                .filter { id -> watchList.any { it.movieId == id } }
                .toSet()
            watchlistIds = inWatchlist
        }
    }
}
