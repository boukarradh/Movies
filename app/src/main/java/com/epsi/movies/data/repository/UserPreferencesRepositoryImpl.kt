package com.epsi.movies.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.epsi.movies.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject // Pour l'injection Hilt
import javax.inject.Singleton // Pour le scope Singleton Hilt



/**
 * Clés utilisées pour accéder aux données dans Preferences DataStore.
 * Définies ici pour être utilisées par l'implémentation.
 */
private object PreferencesKeys {
    val FAVORITE_MOVIE_IDS = stringSetPreferencesKey("favorite_movie_ids")
}

/**
 * Implémentation concrète de [UserPreferencesRepository].
 * Gère la lecture et l'écriture des préférences utilisateur (ici, les favoris)
 * dans Preferences DataStore.
 *
 * @param dataStore L'instance de DataStore<Preferences> injectée par Hilt.
 */
class UserPreferencesRepositoryImpl @Inject constructor( // Constructeur pour l'injection Hilt
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {

    /**
     * Flow qui émet l'ensemble actuel des IDs de films favoris (stockés sous forme de Strings).
     * Gère les erreurs potentielles lors de la lecture du DataStore.
     */
    override val favoriteMovieIdsFlow: Flow<Set<String>> = dataStore.data
        .catch { exception ->
            // Gère les erreurs IO lors de la lecture du fichier DataStore
            if (exception is IOException) {
                // Émet un ensemble de préférences vide pour permettre au Flow de continuer
                // ou de fournir une valeur par défaut à l'utilisateur.
                emit(emptyPreferences())
            } else {
                // Relance les autres types d'exceptions (erreurs inattendues)
                throw exception
            }
        }
        .map { preferences ->
            // Extrait le Set<String> associé à notre clé.
            // Si la clé n'existe pas (première ouverture, etc.), retourne un ensemble vide.
            preferences[PreferencesKeys.FAVORITE_MOVIE_IDS] ?: emptySet()
        }


    /**
     * Supprime un ID de film de l'ensemble des favoris de manière asynchrone.
     */
    override suspend fun removeFavoriteMovie(movieId: Int) {
        try {
            dataStore.edit { preferences ->
                val currentFavorites = preferences[PreferencesKeys.FAVORITE_MOVIE_IDS] ?: emptySet()
                // Supprime l'ID converti en String du Set existant (crée un nouveau Set)
                preferences[PreferencesKeys.FAVORITE_MOVIE_IDS] = currentFavorites - movieId.toString()
            }
        } catch (exception: IOException) {
            // Gérer l'erreur si nécessaire
        }
    }

    /**
     * Bascule l'état de favori pour un ID de film donné de manière asynchrone.
     */
    override suspend fun toggleFavoriteMovie(movieId: Int) {
        try {
            dataStore.edit { preferences ->
                val currentFavorites = preferences[PreferencesKeys.FAVORITE_MOVIE_IDS] ?: emptySet()
                val movieIdStr = movieId.toString()

                val newFavorites: Set<String> = if (currentFavorites.contains(movieIdStr)) {
                    // Si l'ID est déjà présent, on le retire
                    currentFavorites - movieIdStr
                } else {
                    // Si l'ID n'est pas présent, on l'ajoute
                    currentFavorites + movieIdStr
                } // Déclare le nouveau set
                // Met à jour les préférences avec le nouvel ensemble
                preferences[PreferencesKeys.FAVORITE_MOVIE_IDS] = newFavorites
            }
        } catch (exception: IOException) {
             // Gérer l'erreur si nécessaire
        }
    }

}
