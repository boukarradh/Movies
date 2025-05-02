package com.epsi.movies.domain.useCase

import com.epsi.movies.domain.model.movies.MovieUiModel
import com.epsi.movies.domain.repository.MovieRepository
import com.epsi.movies.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * Use Case pour obtenir la liste des films (depuis la base de données) combinée
 * avec l'état actuel des favoris (depuis DataStore).
 *
 * Ce Use Case observe les deux sources de données et émet une liste mise à jour
 * de MovieUiModel chaque fois que l'une ou l'autre source change.
 *
 * @param movieRepository Le repository pour accéder aux données des films (base de données).
 * @param userPreferencesRepository Le repository pour accéder aux préférences utilisateur (favoris).
 */
class GetListOfMoviesUceCase @Inject constructor(
    private val movieRepository: MovieRepository, // Dépendance injectée
    private val userPreferencesRepository: UserPreferencesRepository // Dépendance injectée
) {

    /**
     * Exécute le Use Case.
     * Combine le flux des films depuis la base de données et le flux des IDs favoris
     * pour produire un flux de listes de MovieUiModel avec l'état 'isFavorite' correctement défini.
     *
     * @return Un Flow émettant la List<MovieUiModel> mise à jour.
     */
    operator fun invoke(): Flow<List<MovieUiModel>> {
        // Récupère le Flow des films depuis le repository (supposant qu'il retourne Flow<List<MovieUiModel>>)
        // Assurez-vous que movieRepository.getMovies() existe et retourne le bon type de Flow.
        // Si getMovies retourne Flow<List<MovieUiModel>> sans le favori, adaptez en conséquence.
        // Ici, on suppose qu'il retourne Flow<List<MovieUiModel>> pour faire le mapping ici.
        val moviesFlow = movieRepository.getMovies() // Supposons Flow<List<MovieUiModel>>

        // Récupère le Flow des IDs favoris (Set<String>) depuis le UserPreferencesRepository
        val favoriteIdsFlow: Flow<Set<String>> = userPreferencesRepository.favoriteMovieIdsFlow

        // Combine les deux Flows. Le lambda sera appelé chaque fois que l'un ou l'autre Flow émet une nouvelle valeur.
        return combine(moviesFlow, favoriteIdsFlow) { moviesFromDb, favoriteIdsSet ->
            // moviesFromDb est la dernière List<MovieUiModel> émise.
            // favoriteIdsSet est le dernier Set<String> d'IDs favoris émis.

            // Mappe la liste des MovieUiModel en MovieUiModel
            moviesFromDb.map { moviesList ->
                // Pour chaque film, vérifie si son ID (converti en String) est dans l'ensemble des favoris
                val isFavorite = favoriteIdsSet.contains(moviesList.id.toString())

                //  met à jour le champ isFavorite.
                // Note: Il faut adapter MovieUiModel ou la fonction toUiModel pour inclure/modifier isFavorite.
                // Pour l'instant, on appelle toUiModel et on suppose qu'on peut créer une copie modifiée.
                moviesList.copy(isFavorite = isFavorite)
            }
        }
    }
}