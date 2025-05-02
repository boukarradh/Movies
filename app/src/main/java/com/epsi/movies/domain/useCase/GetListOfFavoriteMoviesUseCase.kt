package com.epsi.movies.domain.useCase

import com.epsi.movies.domain.model.movies.MovieUiModel
import com.epsi.movies.domain.repository.MovieRepository
import com.epsi.movies.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * Use Case pour obtenir la liste des films favoris.
 *
 * Ce Use Case observe les films de la base de données et les IDs des favoris,
 * puis filtre la liste pour ne renvoyer que les films marqués comme favoris.
 *
 * @param movieRepository Repository pour accéder aux données des films (base de données).
 * @param userPreferencesRepository Repository pour accéder aux préférences utilisateur (favoris).
 */
class GetListOfFavoriteMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    /**
     * Exécute le Use Case et renvoie un flux de la liste des films favoris.
     * Il combine le flux de tous les films avec le flux des IDs favoris
     * pour filtrer et renvoyer uniquement les films favoris.
     *
     * @return Un Flow émettant la liste des films favoris.
     */
    operator fun invoke(): Flow<List<MovieUiModel>> {
        val moviesFlow = movieRepository.getMovies()
        val favoriteIdsFlow = userPreferencesRepository.favoriteMovieIdsFlow

        return combine(moviesFlow, favoriteIdsFlow) { moviesFromDb, favoriteIdsSet ->
            moviesFromDb
                .filter { movie ->
                    favoriteIdsSet.contains(movie.id.toString())
                }.map {
                    it.copy(isFavorite = true)
                }
        }
    }
}
