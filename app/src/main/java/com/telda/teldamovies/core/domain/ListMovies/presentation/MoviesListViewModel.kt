package com.telda.teldamovies.core.domain.ListMovies.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.telda.teldamovies.core.data.model.Movie
import com.telda.teldamovies.core.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    var movies by mutableStateOf<List<Movie>>(emptyList())
        private set

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    fun loadPopularMovies(language: String = "en-US") {
        if (isLoading || isLastPage) return

        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies(language, currentPage)
                val newMovies = response.results

                if (newMovies.isEmpty()) {
                    isLastPage = true
                } else {
                    movies = movies + newMovies
                    currentPage++
                }
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
            movies = repository.searchMovies(query).results
        }
    }
}
