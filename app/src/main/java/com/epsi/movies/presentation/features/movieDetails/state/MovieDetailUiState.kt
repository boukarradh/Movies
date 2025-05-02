package com.epsi.movies.presentation.features.movieDetails.state

import com.epsi.movies.domain.model.movies.MovieDetailsUiModel

/**
 * Sealed Interface pour représenter les différents états possibles de l'UI
 * de l'écran affichant le detail d'un film.
 */
sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState // État de chargement des données
    data class Success(val movieDetail: MovieDetailsUiModel) : MovieDetailUiState // État de succès avec le détail du film
    data class Error(val message: String) : MovieDetailUiState // État d'erreur lors du chargement
}