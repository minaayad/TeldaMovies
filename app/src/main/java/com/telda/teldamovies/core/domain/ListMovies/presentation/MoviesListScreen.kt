package com.telda.teldamovies.core.domain.ListMovies.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.telda.teldamovies.R
import com.telda.teldamovies.core.data.model.Movie

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesListScreen(
    viewModel: MoviesListViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val movies = viewModel.movies

    // Group movies by year (descending)
    val moviesByYear = movies.groupBy { movie ->
        movie.release_date?.take(4) ?: "Unknown"
    }.toSortedMap(compareByDescending { it })

    LaunchedEffect(Unit) {
        viewModel.updateWatchlistStatus()
    }

    Scaffold(
        modifier = Modifier.background(Color.Transparent),
        topBar = {
            TopAppBar(
                title = { Text("Telda Movies", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 1f)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search bar
            TextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    if (query.isEmpty()) {
                        viewModel.resetPagination()
                        viewModel.loadPopularMovies()
                    } else {
                        viewModel.searchMovies(query)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp),
                placeholder = { Text("Search movies...") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface)
            )

            if (searchQuery.isNotEmpty() && movies.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No match found",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ) {
                    moviesByYear.forEach { (year, moviesOfYear) ->
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(
                                text = year,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                color = Color.Black
                            )
                        }
                        items(moviesOfYear.size) { index ->
                            val movie = moviesOfYear[index]
                            MovieGridItem(
                                movie = movie,
                                onClick = { onMovieClick(movie) },
                                isMovieInWatchlist = viewModel.watchlistIds.contains(movie.id)
                            )

                            // Keep pagination logic here, using the global movies list
                            val globalIndex = movies.indexOf(movie)
                            if (globalIndex >= movies.size - 5 && searchQuery.isEmpty()) {
                                LaunchedEffect(Unit) {
                                    viewModel.loadPopularMovies()
                                }
                            }
                        }
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
