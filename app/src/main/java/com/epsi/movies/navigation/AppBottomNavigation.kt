package com.epsi.movies.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.epsi.movies.navigation.routes.Screen

/**
 * Composable affichant la barre de navigation inférieure.
 *
 * @param navController Le NavController qui contrôle la navigation de la bottom bar (bottomNavController).
 */
@Composable
fun AppBottomNavigation(navController: NavHostController) {
    // Liste des écrans à afficher dans la barre (ceux avec une icône et un label)
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Favorites
    )

    NavigationBar { // Utilise NavigationBar de Material 3
        // Observe l'état actuel de la pile arrière du navController de la bottom bar
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        // Récupère la destination actuelle
        val currentDestination = navBackStackEntry?.destination

        // Crée un item pour chaque écran dans la liste
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    // Affiche l'icône définie dans la classe Screen
                    screen.icon?.let { Icon(it, contentDescription = screen.label) }
                },
                label = { Text(screen.label) }, // Affiche le label défini dans la classe Screen
                // Détermine si cet item est sélectionné en comparant la route de l'item
                // avec la hiérarchie de la route actuelle (utile pour les graphes imbriqués)
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                // Action déclenchée lors du clic sur l'item
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination pour éviter d'empiler les écrans
                        // lors de la navigation via la bottom bar.
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true // Sauvegarde l'état de l'écran quitté
                        }
                        // Évite de recréer l'écran s'il est déjà au sommet de la pile
                        launchSingleTop = true
                        // Restaure l'état de l'écran si on y revient
                        restoreState = true
                    }
                }
            )
        }
    }
}