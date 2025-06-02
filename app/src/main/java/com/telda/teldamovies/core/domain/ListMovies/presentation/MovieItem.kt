package com.telda.teldamovies.core.domain.ListMovies.presentation

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.telda.teldamovies.R
import com.telda.teldamovies.core.data.model.Movie
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun MovieGridItem(
    movie: Movie,
    onClick: () -> Unit,
    isMovieInWatchlist: Boolean = false,
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .width(200.dp)
            .padding(10.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            SubcomposeAsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                contentDescription = movie.title,
                modifier = Modifier
                    .size(200.dp,250.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .size(200.dp,250.dp)
                                .background(androidx.compose.ui.graphics.Color.White.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(androidx.compose.ui.graphics.Color.White.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.foundation.Image(
                                painter = painterResource(id = R.drawable.ic_placeholder),
                                contentDescription = "Placeholder image",
                                modifier = Modifier
                                    .size(200.dp,250.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(androidx.compose.ui.graphics.Color.LightGray.copy(alpha = 0.7f))
                    .padding(6.dp)
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    style = TextStyle(
                        shadow = Shadow(
                            Color(0xFFFFFFFF),
                            offset = Offset(1f, 1f),
                            5f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "",
                        tint = androidx.compose.ui.graphics.Color.Yellow
                    )
                    Text(
                        text = movie.vote_average.toString(),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.Black,
                        maxLines = 2
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Icon(
                            imageVector = if (isMovieInWatchlist) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isMovieInWatchlist) "In Watchlist" else "Not in Watchlist",
                            tint = if (isMovieInWatchlist) androidx.compose.ui.graphics.Color.Red else androidx.compose.ui.graphics.Color.Black,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(end = 8.dp)
                        )

                    }

                }

            }
        }
    }
}