package com.telda.teldamovies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.telda.teldamovies.core.domain.ListMovies.presentation.MoviesListScreen
import com.telda.teldamovies.core.domain.MovieDetails.presentation.DetailsScreen

sealed class Screen(val route: String) {
    object MoviesList : Screen("movies_list")
    object MovieDetails : Screen("movie_details/{movieId}") {
        fun createRoute(movieId: Int) = "movie_details/$movieId"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MoviesList.route
    ) {
        composable(Screen.MoviesList.route) {
            MoviesListScreen(
                onMovieClick = { movie ->
                    navController.navigate(Screen.MovieDetails.createRoute(movie.id))
                }
            )
        }
        
        composable(
            route = Screen.MovieDetails.route,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
            DetailsScreen(
                movieId = movieId,
                navController = navController
            )
        }
    }
} 