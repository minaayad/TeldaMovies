package com.telda.teldamovies.core.domain.ListMovies.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.telda.teldamovies.core.data.model.Movie

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(
    viewModel: MoviesListViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val movies = viewModel.movies

    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar at the top
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                viewModel.searchMovies(query)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Search movies...") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(movies) { index, movie ->
                MovieItem(movie = movie, isInWatchlist = false) {
                    onMovieClick(movie)
                }

                // Trigger pagination when the user scrolls near the end
                if (index >= movies.size - 5 && searchQuery.isEmpty()) {
                    LaunchedEffect(Unit) {
                        viewModel.loadPopularMovies()
                    }
                }
            }
        }
    }

    // Initial load when no search query
    LaunchedEffect(Unit) {
        if (movies.isEmpty() && searchQuery.isEmpty()) {
            viewModel.loadPopularMovies()
        }
    }
}


@Composable
fun MoviesListScreenold(viewModel: MoviesListViewModel = hiltViewModel(), onMovieClick: (Movie) -> Unit) {
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