package com.telda.teldamovies.core.domain.ListMovies.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.telda.teldamovies.core.data.model.Movie

@Composable
fun MoviesListScreen(viewModel: MoviesListViewModel = hiltViewModel(), onMovieClick: (Movie) -> Unit) {
    val movies = viewModel.movies

    LazyColumn {
        itemsIndexed(movies) { index, movie ->
            MovieItem(movie = movie, isInWatchlist = false) {
                onMovieClick(movie)
            }

            // Trigger pagination when the user scrolls near the end
            if (index >= movies.size - 5) {
                LaunchedEffect(Unit) {
                    viewModel.loadPopularMovies()
                }
            }
        }
    }

    // Initial load
    LaunchedEffect(Unit) {
        if (movies.isEmpty()) {
            viewModel.loadPopularMovies()
        }
    }
}
