package com.epsi.movies.navigation // Adaptez le package si besoin

// Importez vos écrans réels ou placeholders
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.epsi.movies.navigation.routes.Screen
import com.epsi.movies.presentation.MainAppScreen
import com.epsi.movies.presentation.features.home.MovieDetailScreen
import com.epsi.movies.presentation.features.login.LoginScreen
import com.epsi.movies.presentation.features.register.RegistrationScreen

/**
 * Composable responsable de la navigation principale de l'application.
 * Il détermine l'écran de départ (Login ou Register) et gère la navigation
 * entre les écrans d'authentification et l'écran principal de l'application.
 *
 * @param navController Le contrôleur de navigation principal.
 * @param startDestination La route de l'écran de démarrage (déterminée en fonction de l'existence de l'utilisateur).
 */
@Composable
fun AppNavigation(
    navController: NavHostController, // Le contrôleur de navigation
    startDestination: String // La route de départ (ex: Screen.Login.route ou Screen.Register.route)
) {
    // NavHost définit le graphe de navigation principal
    NavHost(
        navController = navController,
        startDestination = startDestination // Définit l'écran initial
    ) {
        // --- Destination : Écran de Connexion ---
        composable(route = Screen.Login.route) {
            // Affiche le composable LoginScreen
            LoginScreen(
                // Passe une fonction lambda pour naviguer vers MainApp en cas de succès
                onLoginSuccess = {
                    navController.navigate(Screen.MainApp.route) {
                        // Supprime LoginScreen de la pile arrière pour ne pas y revenir
                        popUpTo(Screen.Login.route) { inclusive = true }
                        // Évite de créer plusieurs instances de MainApp si on clique vite
                        launchSingleTop = true
                    }
                },
                // Passe une fonction lambda pour naviguer vers RegisterScreen
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                    // Optionnel: popUpTo(Screen.Login.route) { inclusive = true } si on veut remplacer Login par Register
                }
                // Vous passerez le ViewModel ici plus tard
            )
        }

        // --- Destination : Écran d'Inscription ---
        composable(route = Screen.Register.route) {
            // Affiche le composable RegisterScreen
            RegistrationScreen(
                // Passe une fonction lambda pour naviguer vers MainApp en cas de succès
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        // Supprime RegisterScreen de la pile arrière
                        popUpTo(Screen.Register.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }

        // --- Destination : Écran Principal de l'Application ---
        // Cet écran contiendra la Bottom Navigation Bar et son propre NavHost interne
        composable(route = Screen.MainApp.route) {
            // Affiche le composable MainAppScreen
            MainAppScreen(rootNavController = navController) // Passe le navController principal si besoin (ex: pour logout ou détails)
        }

        // --- Destination : Écran de Détail d'un Film ---
        // Notez la définition de l'argument "movieId" dans la route
        composable(
            route = Screen.MovieDetail.route + "/{movieId}", // Ex: "movie_detail_screen/{movieId}"
            arguments = listOf(navArgument("movieId") { // Définit l'argument attendu
                type = NavType.StringType // Ou NavType.IntType si vous préférez passer un Int
                nullable = false // L'ID ne peut pas être null ici
            })
        ) { backStackEntry -> // Permet d'accéder aux arguments passés
            // Récupère l'argument "movieId" depuis la pile arrière
            val movieId = backStackEntry.arguments?.getString("movieId")
            // Affiche le composable MovieDetailScreen en passant l'ID
            MovieDetailScreen(
                navController = navController, // Passe le navController pour pouvoir revenir en arrière
                movieId = movieId
            )
        }

        // Ajoutez ici d'autres destinations de haut niveau si nécessaire
    }
}



