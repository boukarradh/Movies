package com.epsi.movies.presentation.features.searshMovies.state

import com.epsi.movies.domain.model.movies.MovieUiModel

/**
 * Sealed Interface pour représenter les différents états possibles de l'UI
 * de l'écran affichant la liste des films.
 */
sealed interface SearchMovieUiState {
    data object Loading : SearchMovieUiState // État de chargement initial ou pendant le refresh
    data class Success(val movies: List<MovieUiModel>) : SearchMovieUiState // État de succès avec la liste des films
    data object Empty : SearchMovieUiState // État vide (aucun film trouvé)
    data class Error(val message: String) : SearchMovieUiState // État d'erreur
    data object Nothing : SearchMovieUiState // Page initiale
}