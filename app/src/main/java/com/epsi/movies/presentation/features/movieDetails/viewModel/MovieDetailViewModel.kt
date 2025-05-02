package com.epsi.movies.presentation.features.movieDetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epsi.movies.domain.repository.MovieRepository
import com.epsi.movies.presentation.features.movieDetails.state.MovieDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour l'écran des détails du film.
 *
 * @param repository Le repository pour accéder aux données des films.
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {
    private val _movieDetails: MutableStateFlow<MovieDetailUiState> =
        MutableStateFlow(MovieDetailUiState.Loading)
    /**
     * Flux de données observable contenant l'état de l'interface utilisateur pour les détails du film.
     *
     *  Émet les états [MovieDetailUiState].
     */
    val movieDetails = _movieDetails.asStateFlow()

    /**
     * Récupère les détails d'un film à partir de son ID.
     *
     * @param movieId L'ID du film à récupérer.
     *
     * Met à jour le flux [_movieDetails] avec les données récupérées ou une erreur.
     */
    fun getMovieDetails(movieId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.value = MovieDetailUiState.Loading
          val response = movieId?.toInt()?.let { repository.getMovieDetails(it) }
            if (response != null) {
                _movieDetails.value = MovieDetailUiState.Success(response)
            } else {
                _movieDetails.value = MovieDetailUiState.Error("Error")
            }
        }
    }

}