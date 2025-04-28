package com.epsi.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.epsi.movies.navigation.AppNavigation
import com.epsi.movies.navigation.routes.Screen
import com.epsi.movies.presentation.MainViewModel
import com.epsi.movies.presentation.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesTheme {
                R.drawable.img
                // Surface est souvent utilisée comme conteneur racine dans Material Design
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Appelle le composable qui gère le point d'entrée et la navigation
                    AppEntry()
                }
            }
        }
    }
}

@Composable
fun AppEntry(mainViewModel: MainViewModel = hiltViewModel()) { // Obtient le ViewModel via Hilt
    // Observe l'état d'existence de l'utilisateur depuis le ViewModel
    val userExistsState =  mainViewModel.userExists.collectAsState()

    // Affiche un indicateur de chargement tant que l'état initial (null) n'a pas été résolu
    if (userExistsState.value == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // Une fois l'état connu (true ou false), configure et lance la navigation
        val navController = rememberNavController() // Crée le contrôleur de navigation
        // Détermine la route de départ
        val startDestination = if (userExistsState.value == true) Screen.Login.route else Screen.Register.route
        // Appelle le composable NavHost principal
        AppNavigation(navController = navController, startDestination = startDestination)
    }
}
