package com.epsi.movies.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector


/**
 * Sealed class définissant toutes les destinations (écrans) possibles
 * et leurs routes associées dans l'application.
 *
 * Utiliser une sealed class permet d'avoir un contrôle type-safe sur les routes
 * et de regrouper les informations liées à chaque écran (route, label, icône).
 *
 * @param route La chaîne de caractères unique identifiant l'écran pour le système de navigation.
 * @param label Un nom lisible pour l'écran (optionnel, utile pour la Bottom Bar).
 * @param icon Une icône Material pour l'écran (optionnel, utile pour la Bottom Bar).
 */
sealed class Screen(val route: String, val label: String = "", val icon: ImageVector? = null) {

    // Écran d'inscription
    data object Register : Screen(route = "register_screen", label = "Inscription")

    // Écran de connexion
    data object Login : Screen(route = "login_screen", label = "Connexion")

    // Représente la section principale de l'application après connexion/inscription
    // (qui contiendra la Bottom Navigation et son propre NavHost)
    data object MainApp : Screen(route = "main_app_screen")

    // Écrans accessibles via la Bottom Navigation Bar
    data object Home : Screen(route = "home_screen", label = "Accueil", icon = Icons.Filled.Home)
    data object Search : Screen(route = "search_screen", label = "Recherche", icon = Icons.Filled.Search)
    data object Favorites : Screen(route = "favorites_screen", label = "Favoris", icon = Icons.Filled.Favorite)

    // Écran pour afficher les détails d'un film.
    // La route de base est définie ici. L'ID sera ajouté lors de la navigation.
    data object MovieDetail : Screen(route = "movie_detail_screen", label = "Détails")

    // --- Helper pour construire la route complète avec arguments (bonne pratique) ---
    // Exemple pour l'écran de détail qui attend un movieId
    fun withArgs(vararg args: String): String {
        // Construit la route en ajoutant les arguments séparés par des slashes
        // Exemple: Screen.MovieDetail.withArgs("123") -> "movie_detail_screen/123"
        return buildString {
            append(route) // Ajoute la route de base
            args.forEach { arg ->
                append("/$arg") // Ajoute chaque argument
            }
        }
    }
    // Surcharge pour les arguments de type Int
    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}