package com.telda.teldamovies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.telda.teldamovies.core.domain.ListMovies.presentation.MoviesListScreen
import com.telda.teldamovies.ui.theme.TeldaMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MoviesListScreen(onMovieClick = { movie ->
                    // TODO: Handle movie click, e.g., navigate to detail screen
                    Log.d("MainActivity", "Clicked on movie: ${movie.title}")
                })
            }
        }
    }
}
