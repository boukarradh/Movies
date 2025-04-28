package com.epsi.movies.presentation.features.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController, onMovieClick: (String) -> Unit) {
        Text(
        text = "Welcome to Home Screen!",
        modifier = modifier
    )
}