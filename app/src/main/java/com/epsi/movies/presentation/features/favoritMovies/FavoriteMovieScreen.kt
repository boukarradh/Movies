package com.epsi.movies.presentation.features.favoritMovies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.epsi.movies.domain.model.movies.MovieUiModel
import com.epsi.movies.presentation.features.favoritMovies.viewModel.FavoriteMoviesViewModel
import com.epsi.movies.presentation.features.home.state.MoviesListUiState
import com.epsi.movies.presentation.ui.designsystem.MovieCard

@Composable
fun FavoriteMovieScreen(
    modifier: Modifier = Modifier,
    favoriteMoviesViewModel: FavoriteMoviesViewModel = hiltViewModel(),
    navController: NavHostController,
    onMovieClick: (String) -> Unit
) {
    val moviesState = favoriteMoviesViewModel.moviesUiState.collectAsStateWithLifecycle().value
    when (moviesState) {
        is MoviesListUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is MoviesListUiState.Success -> {
            FavoriteMoviesListLayout(
                moviesList = moviesState.movies,
                onMovieClick = onMovieClick,
                onFavoriteClick = { movieId ->
                    favoriteMoviesViewModel.addMovieToFavorites(movieId)
                })
        }

        is MoviesListUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(modifier = Modifier.align(Alignment.Center), text = moviesState.message)
            }
        }
    }
}

@Composable
fun FavoriteMoviesListLayout(
    moviesList: List<MovieUiModel>, onMovieClick: (String) -> Unit, onFavoriteClick: (Int) -> Unit
) {

    // LazyColumn est l'équivalent Compose de RecyclerView, optimisé pour afficher
    // de longues listes sans consommer trop de mémoire.
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // Prend tout l'espace disponible
        contentPadding = PaddingValues(vertical = 8.dp) // Ajoute un peu d'espace en haut et en bas de la liste
    ) {
        // 'items' est une fonction d'extension pour LazyListScope (le contexte de LazyColumn)
        // qui permet de créer un item pour chaque élément dans une liste.
        // 'state.movies' est la List<MovieUiModel> provenant de votre état UI.
        items(items = moviesList) { movie -> // Ce lambda est appelé pour chaque 'movie' dans la liste 'state.movies'

            MovieCard(
                movieId = movie.id,
                title = movie.title,
                posterUrl = movie.posterUrl, // Utilise l'URL déjà construite dans MovieUiModel
                releaseDateFormatted = movie.releaseDateFormatted, // Utilise la date formatée
                voteAverageFormatted = movie.voteAverageFormatted, // Utilise la note formatée
                isFavorite = movie.isFavorite,
                onCardClick = { movieId ->
                    onMovieClick(movieId.toString())

                },
                onFavoriteClick = { movieId ->
                    onFavoriteClick(movieId)
                })
        }
    }
}