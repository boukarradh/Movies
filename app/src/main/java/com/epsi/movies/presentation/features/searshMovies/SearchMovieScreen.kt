package com.epsi.movies.presentation.features.searshMovies

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SearchMovieScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onMovieClick: (String)->Unit
) {
        Text(
        text = "Welcome to Search Movie Screen!",
        modifier = modifier
    )
}