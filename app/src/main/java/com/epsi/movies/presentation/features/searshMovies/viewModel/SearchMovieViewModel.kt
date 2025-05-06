package com.epsi.movies.presentation.features.searshMovies.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epsi.movies.domain.repository.MovieRepository
import com.epsi.movies.domain.repository.UserPreferencesRepository
import com.epsi.movies.presentation.features.searshMovies.state.SearchMovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val preferencesRepository: UserPreferencesRepository

) : ViewModel() {

    private val _searchResults = MutableStateFlow<SearchMovieUiState>(SearchMovieUiState.Loading)
    val searchResults: StateFlow<SearchMovieUiState> = _searchResults.asStateFlow()

    fun searchMovies(query: String) {
        _searchResults.value = SearchMovieUiState.Loading
        viewModelScope.launch {
        val response =repository.searchMovies(query = query)
            if(response.isNotEmpty()){
                _searchResults.value = SearchMovieUiState.Success(response)
            }
            else{
                _searchResults.value = SearchMovieUiState.Empty
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

    fun resetSearchState(){
        _searchResults.value = SearchMovieUiState.Empty
    }
}