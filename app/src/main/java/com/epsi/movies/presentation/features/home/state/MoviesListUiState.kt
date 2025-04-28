package com.epsi.movies.presentation.features.home.state

import com.epsi.movies.domain.model.movies.MovieUiModel

/**
 * Sealed Interface pour représenter les différents états possibles de l'UI
 * de l'écran affichant la liste des films.
 */
sealed interface MoviesListUiState {
    data object Loading : MoviesListUiState // État de chargement initial ou pendant le refresh
    data class Success(val movies: List<MovieUiModel>) : MoviesListUiState // État de succès avec la liste des films
    data class Error(val message: String) : MoviesListUiState // État d'erreur
}