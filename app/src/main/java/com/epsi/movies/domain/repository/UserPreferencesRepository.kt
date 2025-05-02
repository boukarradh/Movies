package com.epsi.movies.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Interface définissant le contrat pour accéder et modifier les préférences utilisateur
 * stockées via DataStore.
 */
interface UserPreferencesRepository {

    /**
     * Un Flow qui émet l'ensemble actuel des IDs des films marqués comme favoris.
     * Les IDs sont stockés et émis sous forme de Strings.
     * Le Flow émettra une nouvelle valeur chaque fois que l'ensemble des favoris change.
     *
     * @return Un Flow émettant un Set<String> contenant les IDs des films favoris.
     * Émet un ensemble vide si aucun favori n'est enregistré ou en cas d'erreur de lecture initiale.
     */
    val favoriteMovieIdsFlow: Flow<Set<String>>


    /**
     * Supprime un ID de film de l'ensemble des favoris de manière asynchrone.
     *
     * @param movieId L'ID du film à supprimer.
     */
    suspend fun removeFavoriteMovie(movieId: Int)

    /**
     * Bascule l'état de favori pour un ID de film donné de manière asynchrone.
     * Ajoute l'ID s'il n'est pas présent, le supprime s'il est présent.
     *
     * @param movieId L'ID du film dont l'état favori doit être basculé.
     */
    suspend fun toggleFavoriteMovie(movieId: Int)


}