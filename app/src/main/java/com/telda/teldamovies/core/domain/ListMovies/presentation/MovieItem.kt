package com.telda.teldamovies.core.domain.ListMovies.presentation

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.telda.teldamovies.core.data.model.Movie
import com.telda.teldamovies.core.di.NetworkModule

@Composable
fun MovieItem(movie: Movie, isInWatchlist: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(Modifier.padding(8.dp)) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            Log.e("imageUrl", imageUrl)
            AsyncImage(
                model = imageUrl,
                contentDescription = movie.title,
                modifier = Modifier.size(100.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Text(text = movie.overview.take(100) + "...", style = MaterialTheme.typography.bodyMedium)
                if (isInWatchlist) {
                    Text("★ In Watchlist", color = androidx.compose.ui.graphics.Color.Yellow)
                }
            }
        }
    }
}