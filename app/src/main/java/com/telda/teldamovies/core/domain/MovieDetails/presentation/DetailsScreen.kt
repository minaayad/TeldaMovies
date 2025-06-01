package com.telda.teldamovies.core.domain.MovieDetails.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movieId: Int,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val details = viewModel.movieDetails
    val similar = viewModel.similarMovies
    val actors = viewModel.topActors
    val directors = viewModel.topDirectors

    LaunchedEffect(movieId) {
        viewModel.loadMovieData(movieId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(details?.title ?: "Movie Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Section 1: Movie Details
            item {
                details?.let {
                    Text(text = it.title, style = MaterialTheme.typography.headlineSmall)
                    it.tagline?.let { Text("$it", style = MaterialTheme.typography.bodyMedium) }
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                        contentDescription = it.title,
                        modifier = Modifier.fillMaxWidth().height(300.dp)
                    )
                    Text("Overview: ${it.overview}", style = MaterialTheme.typography.bodyMedium)
                    Text("Revenue: $${it.revenue ?: 0}", style = MaterialTheme.typography.bodyMedium)
                    Text("Release: ${it.releaseDate}", style = MaterialTheme.typography.bodySmall)
                    Text("Status: ${it.status}", style = MaterialTheme.typography.bodySmall)
                    Button(onClick = { viewModel.addToWatchList() }) {
                        Text("Add to Watchlist")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Section 2: Similar Movies
            item {
                Text("Similar Movies", style = MaterialTheme.typography.titleMedium)
            }
            items(similar) {
                Text(it.title, modifier = Modifier.padding(vertical = 4.dp))
            }

            // Section 3: Cast Grouped (Actors & Directors)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Top Actors", style = MaterialTheme.typography.titleMedium)
            }
            items(actors) {
                Text("${it.name} (popularity: ${it.popularity})")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Top Directors", style = MaterialTheme.typography.titleMedium)
            }
            items(directors) {
                Text("${it.name} (popularity: ${it.popularity})")
            }
        }
    }
}