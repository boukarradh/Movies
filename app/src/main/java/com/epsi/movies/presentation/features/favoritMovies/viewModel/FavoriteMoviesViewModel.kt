package com.epsi.movies.presentation.features.favoritMovies.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epsi.movies.domain.repository.UserPreferencesRepository
import com.epsi.movies.domain.useCase.GetListOfFavoriteMoviesUseCase
import com.epsi.movies.presentation.features.home.state.MoviesListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val favoriteMoviesFlow: GetListOfFavoriteMoviesUseCase,
    private val preferencesRepository: UserPreferencesRepository
) :
    ViewModel() {

    // StateFlow mutable privé pour gérer l'état interne du ViewModel
    private val _uiState = MutableStateFlow<MoviesListUiState>(MoviesListUiState.Loading)

    // StateFlow public et immuable exposé à l'UI
    val moviesUiState: StateFlow<MoviesListUiState> = _uiState.asStateFlow()

    // Bloc d'initialisation exécuté lors de la création du ViewModel
    init {
        // Lance l'observation du Flow de la base de données
        observeFavoritMoviesFromDatabase()
    }

    /**
     * Observe le Flow provenant du Repository (qui lit depuis la base de données).
     * Met à jour l'état de l'UI chaque fois que la base de données change.
     */
    private fun observeFavoritMoviesFromDatabase() {
        viewModelScope.launch {
            _uiState.value = MoviesListUiState.Loading
            delay(2000) // Simule un délai de 2000seconds (2 minutes) pour la démonstration
            favoriteMoviesFlow() // Appelle la fonction du repo qui retourne Flow<List<MovieUiModel>>
                .catch { exception ->
                    // Gère les erreurs potentielles lors de la collecte du Flow de la DB
                    _uiState.value =
                        MoviesListUiState.Error("Erreur lors de la lecture du cache: ${exception.message}")
                }
                .collect { movies ->
                    // Met à jour l'état avec la nouvelle liste de films provenant de la DB
                    // Même si la liste est vide initialement, on passe en Success (ou on pourrait garder Loading)
                    _uiState.value = MoviesListUiState.Success(movies)
                }
        }
    }

    /**
     * Bascule l'état de favori pour un film donné.
     * Cette fonction est appelée lorsque l'utilisateur clique sur l'icône étoile
     * dans l'interface utilisateur .
     *
     * @param movieId L'identifiant unique du film dont l'état favori doit être modifié.
     */
    fun addMovieToFavorites(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.toggleFavoriteMovie(movieId)
        }
    }
}