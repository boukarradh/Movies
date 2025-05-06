package com.epsi.movies.presentation.features.searshMovies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epsi.movies.presentation.features.searshMovies.state.SearchMovieUiState
import com.epsi.movies.presentation.features.searshMovies.viewModel.SearchMovieViewModel
import com.epsi.movies.presentation.ui.designsystem.MovieCard
import com.epsi.movies.presentation.ui.theme.MoviesTheme


/**
 * Composable représentant l'écran de recherche de films.
 * Utilise [SearchMovieViewModel] pour gérer l'état et la logique de recherche.
 * Inclut un bouton explicite pour lancer la recherche.
 *
 * @param onMovieClick Lambda appelé lorsqu'une carte de film est cliquée.
 * @param viewModel Instance de [SearchMovieViewModel] injectée par Hilt.
 */
@Composable
fun SearchMovieScreen(
    onMovieClick: (String) -> Unit, // Passe l'ID du film (String ou Int)
    viewModel: SearchMovieViewModel = hiltViewModel() // Injection du ViewModel
) {
    // État pour le champ de texte de recherche (géré localement dans l'UI)
    var searchQuery by remember { mutableStateOf("") }

    // Observe l'état des résultats de recherche depuis le ViewModel
    val searchState by viewModel.searchResults.collectAsStateWithLifecycle()

    // Contrôleur pour le clavier
    val keyboardController = LocalSoftwareKeyboardController.current

    // Fonction lambda pour déclencher la recherche (réutilisée par le bouton et le clavier)
    val triggerSearch = {
        if (searchQuery.isNotBlank()) {
            viewModel.searchMovies(searchQuery)
            keyboardController?.hide() // Masque le clavier après la recherche
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp) // Padding global
    ) {
        // --- Barre de Recherche (TextField + Bouton) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newQuery ->
                    searchQuery = newQuery
                    // Ne déclenche plus la recherche automatiquement ici
                    if (newQuery.isBlank()) {
                        viewModel.resetSearchState()
                    }
                },
                modifier = Modifier.weight(1f), // Prend l'espace disponible moins le bouton
                label = { Text("Rechercher...") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Icône Recherche")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            searchQuery = ""
                             viewModel.resetSearchState()
                        }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Effacer la recherche")
                        }
                    }
                },
                singleLine = true,
                // Action "Rechercher" sur le clavier déclenche la recherche
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { triggerSearch() })
            )

            Spacer(modifier = Modifier.width(8.dp)) // Espace entre TextField et Bouton

            // Bouton pour lancer la recherche explicitement
            Button(
                onClick = triggerSearch,
                enabled = searchQuery.isNotBlank() // Activé seulement si le champ n'est pas vide
            ) {
                Text("Rechercher")
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espace après la barre de recherche

        // --- Zone d'Affichage des Résultats ---
        Box(modifier = Modifier.fillMaxSize()) {
            // Affiche l'UI en fonction de l'état reçu du ViewModel
            when (val state = searchState) {

                is SearchMovieUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is SearchMovieUiState.Empty -> {
                    Text(
                        text = "Aucun résultat trouvé pour \"$searchQuery\"",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is SearchMovieUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is SearchMovieUiState.Success -> {
                    // Affiche la liste des résultats si l'état est Success
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        //to add here
                    }
                }
            }
        }
    }
}
