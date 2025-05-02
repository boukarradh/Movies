package com.epsi.movies.presentation.features.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.epsi.movies.domain.model.movies.MovieDetailsUiModel
import com.epsi.movies.presentation.features.movieDetails.state.MovieDetailUiState
import com.epsi.movies.presentation.features.movieDetails.viewModel.MovieDetailViewModel
import com.epsi.movies.presentation.ui.designsystem.MovieDetailContent
/**
 * Composable représentant l'écran complet d'affichage des détails d'un film.
 * Cet écran gère les différents états de l'interface utilisateur (chargement, succès, erreur)
 * en observant un [MovieDetailViewModel] et délègue l'affichage réel du contenu
 * au composant [MovieDetailsLayout] en cas de succès.
 *
 * @param navController Le contrôleur de navigation (généralement le `rootNavController`)
 * utilisé pour les actions de navigation potentielles (ex: revenir en arrière).
 * @param movieId L'identifiant unique (String ou Int selon votre navigation) du film
 * dont les détails doivent être affichés. Reçu comme argument de navigation.
 * @param viewModel L'instance de [MovieDetailViewModel] responsable de la récupération
 * et de la gestion de l'état des détails du film. Obtenu via Hilt par défaut.
 */
@Composable
fun MovieDetailScreen(
    navController: NavHostController,
    movieId: String?,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    // LaunchedEffect est utilisé pour exécuter une action (ici, charger les détails)
    // une seule fois lorsque le composant entre dans la composition (ou si une clé change).
    // Utiliser movieId comme clé assure que si on navigue vers le détail d'un *autre* film
    // sans quitter complètement l'écran, l'effet sera relancé pour charger les nouvelles données.
    LaunchedEffect(key1 = movieId) {
        viewModel.getMovieDetails(movieId)
    }

    // Observe le StateFlow 'movieDetails' (ou nom similaire) du ViewModel.
    // collectAsStateWithLifecycle est recommandé pour collecter les Flows de manière
    // sûre par rapport au cycle de vie (arrête la collecte en arrière-plan).
    // value contient la dernière valeur émise par le StateFlow (un objet MovieDetailUiState).
    // Chaque nouvelle émission déclenchera une recomposition de ce composable.
    val moviesDetailsState = viewModel.movieDetails.collectAsStateWithLifecycle().value


    // Affiche l'interface utilisateur en fonction de l'état actuel (moviesDetailsState).
    // Le when permet de gérer les différents cas définis dans la sealed interface MovieDetailUiState.
    // Box principale pour permettre la superposition du bouton Retour
    Box(modifier = Modifier.fillMaxSize()) {

        // Contenu central basé sur l'état de l'UI
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // Centre le contenu (Loading/Error)
        ) {
            when (val state = moviesDetailsState) {
                is MovieDetailUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is MovieDetailUiState.Error -> {
                    Text(text = state.message)
                }
                is MovieDetailUiState.Success -> {
                    // Appelle le layout qui affiche le contenu détaillé
                    // Note: MovieDetailsLayout ne contient plus le bouton X
                    MovieDetailsLayout(moviesDetails = state.movieDetail)
                }
            }
        }

        // Bouton de retour (X) toujours affiché en haut à gauche
        IconButton(
            onClick = { navController.popBackStack() }, // Action pour revenir en arrière
            modifier = Modifier
                .align(Alignment.TopStart) // Aligne en haut à gauche
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Retour",
                tint = Color.White // Ajustez la couleur pour le contraste
                // Vous pouvez ajouter un fond si nécessaire pour la visibilité
            )
        }
    }
}

@Composable
fun MovieDetailsLayout(moviesDetails: MovieDetailsUiModel) {
    // Appelle le composant d'affichage principal en lui passant
    // les propriétés extraites de l'objet moviesDetails.
    MovieDetailContent(
        title = moviesDetails.title,
        overview = moviesDetails.overview ?: "", // Utilise l'overview du modèle UI
        posterUrl = moviesDetails.posterUrl, // Utilise l'URL formatée
        backdropUrl = moviesDetails.backdropUrl, // Utilise l'URL formatée
        releaseDateFormatted = moviesDetails.releaseDateFormatted, // Utilise la date formatée
        voteAverageFormatted = moviesDetails.voteAverageFormatted, // Utilise la note formatée
        voteCount = moviesDetails.voteCount, // Utilise le nombre de votes brut
        runtimeFormatted = moviesDetails.runtimeFormatted, // Utilise la durée formatée
        genres = moviesDetails.genres, // Utilise la chaîne des genres formatée
        tagline = moviesDetails.tagline, // Utilise le tagline
    )
}