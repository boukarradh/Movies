package com.epsi.movies.presentation.features.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable fun MovieDetailScreen(navController: NavHostController, movieId: String?) {
    // Votre UI de détail ici...
     Text("Écran Détail Film: ID $movieId")
     Button(onClick = { navController.popBackStack() }) { Text("Retour") }
}