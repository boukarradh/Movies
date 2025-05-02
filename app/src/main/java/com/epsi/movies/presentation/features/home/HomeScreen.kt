package com.epsi.movies.presentation.features.home

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
import com.epsi.movies.presentation.features.home.state.MoviesListUiState
import com.epsi.movies.presentation.features.home.viewModel.MoviesViewModel
import com.epsi.movies.presentation.ui.designsystem.MovieCard

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController, onMovieClick: (String) -> Unit,moviesViewModel: MoviesViewModel = hiltViewModel()) {
    val moviesState  = moviesViewModel.moviesUiState.collectAsStateWithLifecycle().value
    when (moviesState) {
        is MoviesListUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is MoviesListUiState.Success -> {
            MoviesListLayout(
                moviesList = moviesState.movies,
                onFavoriteClick = { movieId ->
                    moviesViewModel.addMovieToFavorites(movieId)
                }
            )
        }
        is MoviesListUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()){
                Text(text = moviesState.message)
            }
        }
    }
}

@Composable
fun MoviesListLayout(moviesList: List<MovieUiModel>,onFavoriteClick: (Int) -> Unit){

}