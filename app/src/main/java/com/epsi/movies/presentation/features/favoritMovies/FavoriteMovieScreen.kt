package com.epsi.movies.presentation.features.favoritMovies

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun FavoriteMovieScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onMovieClick: (String)->Unit
) {
        Text(
        text = "Welcome to Favorite Movie Screen!",
        modifier = modifier
    )
}