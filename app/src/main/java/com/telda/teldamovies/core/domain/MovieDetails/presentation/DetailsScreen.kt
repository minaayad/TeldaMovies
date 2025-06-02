package com.telda.teldamovies.core.domain.MovieDetails.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.telda.teldamovies.core.data.model.Movie
import com.telda.teldamovies.core.domain.ListMovies.presentation.MovieGridItem
import com.telda.teldamovies.core.domain.ListMovies.presentation.MoviesListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movieId: Int,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel(),
    moviesListViewModel: MoviesListViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    val details = viewModel.movieDetails
    val similar = viewModel.similarMovies
    val actors = viewModel.topActors
    val directors = viewModel.topDirectors
    val isInWatchlist = viewModel.isInWatchlist

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
                        model = "https://image.tmdb.org/t/p/w500${it.backdropPath}",
                        contentDescription = it.title,
                        modifier = Modifier.fillMaxWidth().height(300.dp)
                    )
                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = it.overview,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Text(
                        text = "Revenue",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = "$${it.revenue ?: 0}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Text(
                        text = "Release Date",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = it.releaseDate,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Text(
                        text = "Status",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = it.status,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Button(
                        onClick = {
                            if (isInWatchlist) {
                                viewModel.removeFromWatchList()
                            } else {
                                viewModel.addToWatchList()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isInWatchlist) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            if (isInWatchlist) "Remove from Watchlist" else "Add to Watchlist",
                            color = if (isInWatchlist) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Section 2: Similar Movies
            item {
                Text("Similar Movies", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(similar) { movie ->
                        MovieGridItem(
                            movie = movie,
                            onClick = { onMovieClick(movie)},
                            isMovieInWatchlist = moviesListViewModel.watchlistIds.contains(movie.id)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Section 3: Cast Grouped (Actors & Directors)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Top Actors", style = MaterialTheme.typography.titleMedium)
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(actors) { actor ->
                        PersonItem(
                            imageUrl = actor.profilePath,
                            name = actor.name
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Top Directors", style = MaterialTheme.typography.titleMedium)
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(directors) { director ->
                        PersonItem(
                            imageUrl = director.profilePath,
                            name = director.name
                        )
                    }
                }
            }
        }
    }
}