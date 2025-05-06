package com.epsi.movies.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epsi.movies.navigation.AppBottomNavigation
import com.epsi.movies.navigation.routes.Screen
import com.epsi.movies.presentation.features.favoritMovies.FavoriteMovieScreen
import com.epsi.movies.presentation.features.home.HomeScreen
import com.epsi.movies.presentation.features.searshMovies.SearchMovieScreen

/**
 * Composable représentant l'écran principal de l'application après connexion/inscription.
 * Il contient le Scaffold avec la Bottom Navigation Bar et le NavHost interne
 * pour les écrans Home, Search et Favorites.
 *
 * @param rootNavController Le NavController principal (de AppNavigation), passé ici pour
 * permettre aux écrans internes (Home, Search, Favorites) de naviguer vers des écrans
 * externes comme MovieDetail.
 */
@Composable
fun MainAppScreen(rootNavController: NavHostController) {
     // Crée un nouveau NavController spécifiquement pour la navigation de la Bottom Bar
     val bottomNavController = rememberNavController()

     Scaffold(
          // Définit la Bottom Navigation Bar
          bottomBar = { AppBottomNavigation(navController = bottomNavController) }
     ) { innerPadding ->
          // Le contenu principal de l'écran est un NavHost interne
          Box(modifier = Modifier.padding(innerPadding)) {
               // Ce NavHost gère la navigation entre les écrans Home, Search, Favorites
               NavHost(
                    navController = bottomNavController, // Utilise le contrôleur interne
                    startDestination = Screen.Home.route // Définit l'écran de démarrage (Accueil)
               ) {
                    // Destination : Écran d'Accueil
                    composable(Screen.Home.route) {
                         HomeScreen(
                              // Passe le rootNavController pour permettre la navigation vers les détails
                              navController = rootNavController,
                              onMovieClick = { movieId ->
                                   // Construit la route de détail et navigue via le rootNavController
                                   rootNavController.navigate(Screen.MovieDetail.withArgs(movieId))
                              }
                         )
                    }
                    // Destination : Écran de Recherche
                    composable(Screen.Search.route) {
                         SearchMovieScreen(
                              onMovieClick = { movieId ->
                                   rootNavController.navigate(Screen.MovieDetail.withArgs(movieId))
                              }
                         )
                    }
                    // Destination : Écran des Favoris
                    composable(Screen.Favorites.route) {
                         FavoriteMovieScreen(
                              navController = rootNavController,
                              onMovieClick = { movieId ->
                                   rootNavController.navigate(Screen.MovieDetail.withArgs(movieId))
                              }
                         )
                    }
               }
          }
     }
}