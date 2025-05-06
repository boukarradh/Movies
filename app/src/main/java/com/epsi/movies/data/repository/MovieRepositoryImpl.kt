package com.epsi.movies.data.repository

import android.util.Log
import androidx.datastore.core.IOException
import com.epsi.movies.common.Common.LOG_TAG
import com.epsi.movies.data.converter.toEntityList
import com.epsi.movies.data.converter.toUiModel
import com.epsi.movies.data.db.dao.MovieDao
import com.epsi.movies.data.db.entities.MovieEntity
import com.epsi.movies.data.network.MovieDetailsResponseNetwork
import com.epsi.movies.data.network.MovieListResponseNetwork
import com.epsi.movies.data.network.NetworkModule
import com.epsi.movies.data.network.MoviesApiService
import com.epsi.movies.domain.model.movies.MovieDetailsUiModel
import com.epsi.movies.domain.model.movies.MovieUiModel
import com.epsi.movies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Implémentation concrète de MovieRepository.
 * Pour l'instant, récupère les données uniquement depuis l'API TMDB via Retrofit.
 * La logique de cache avec Room sera ajoutée ultérieurement.
 *
 * @param apiService L'instance du service API TMDB.
 */
class MovieRepositoryImpl(
    private val localDataSource: MovieDao,
    private val apiService: MoviesApiService = NetworkModule.moviesApiService
) : MovieRepository {

    /**
     * Récupère les films populaires depuis l'API, les sauvegarde dans la base de données,
     * et retourne true si l'opération a réussi, false sinon.
     */
    override suspend fun fetchPopularMovies(page: Int): Boolean { // Type de retour modifié
        // Exécute l'appel réseau et l'insertion DB sur le thread IO
        return withContext(Dispatchers.IO) {
            try {
                // 1. Appel API
                val networkResponse = apiService.getPopularMovies(page = page)
                Log.d(LOG_TAG, "Réponse API reçue pour les films populaires.")

                // 2. Si l'appel API réussit (networkResponse n'est pas null ET contient des résultats)
                if (networkResponse.results.isNotEmpty()) {
                    // 3. Convertir la réponse réseau en entités pour la base de données
                    val movieEntities = networkResponse.results.toEntityList()
                    Log.d(LOG_TAG, "Conversion en entités (${movieEntities.size} films).")

                    // 4. Insérer les entités dans la base de données Room
                    localDataSource.insertAll(movieEntities)
                    Log.d(LOG_TAG, "Données insérées/mises à jour dans Room.")
                    true // Retourne true si l'insertion réussit
                } else {
                    // L'appel API a réussi mais n'a retourné aucun film, ou la réponse était nulle
                    Log.w(LOG_TAG, "Aucun film populaire reçu de l'API ou réponse nulle.")
                    false // Considéré comme un échec partiel ou état inattendu
                }

            } catch (e: IOException) {
                // Gère les erreurs réseau (ex: pas de connexion)
                Log.e(LOG_TAG, "Erreur réseau lors de la récupération des films populaires: ${e.message}")
                false // Retourne false en cas d'erreur réseau
            } catch (e: Exception) {
                // Gère les autres erreurs potentielles (ex: problème de parsing JSON, erreur DB)
                Log.e(LOG_TAG, "Erreur lors de la récupération ou sauvegarde des films populaires: ${e.message}")
                false // Retourne false en cas d'autre erreur
            }
        }
    }

    /**
     * Recherche des films via l'API TMDB.
     */
    override suspend fun searchMovies(query: String, page: Int): List<MovieUiModel> {
        // Assure que la requête n'est pas vide
        if (query.isBlank()) {
            return emptyList() // Ou retourner une liste vide encapsulée si préféré
        }
        return withContext(Dispatchers.IO) {
            try {
                apiService.searchMovies(query = query, page = page).results.toEntityList()
                    .map { it.toUiModel() }
            } catch (e: IOException) {
                println("Erreur réseau lors de la recherche de films: ${e.message}")
                emptyList()
            } catch (e: Exception) {
                println("Erreur lors de la recherche de films: ${e.message}")
                emptyList()
            }
        }
    }

    /**
     * Récupère les détails d'un film via l'API TMDB et les convertit en MovieUiModel.
     * (Pas de cache ou de sauvegarde implémenté ici pour l'instant)
     */
    override suspend fun getMovieDetails(movieId: Int): MovieDetailsUiModel? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(LOG_TAG, "Appel API pour les détails du film $movieId...")
                // 1. Appel API (Assurez-vous que apiService.getMovieDetails retourne MovieDetailsResponse?)
                val networkResponse: MovieDetailsResponseNetwork? =
                    apiService.getMovieDetails(movieId = movieId)
                Log.d(LOG_TAG, "Réponse API reçue pour les détails du film $movieId.")

                // 2. Convertir la réponse réseau en modèle UI si elle n'est pas nulle
                // Utilise la fonction de mapping .toUiModel() (à définir pour MovieDetailsResponse)
                networkResponse?.toUiModel() // Retourne le MovieUiModel?

            } catch (e: IOException) {
                Log.e(
                    LOG_TAG,
                    "Erreur réseau lors de la récupération des détails du film $movieId: ${e.message}"
                )
                null // Retourne null en cas d'erreur réseau
            } catch (e: Exception) {
                Log.e(
                    LOG_TAG,
                    "Erreur lors de la récupération des détails du film $movieId: ${e.message}"
                )
                null // Retourne null en cas d'autre erreur
            }
        }
    }


    /**
     * Récupère la liste des films depuis la source de données locale (base de données Room)
     * sous forme de Flow et la transforme en une liste de modèles UI (MovieUiModel).
     *
     * Cette fonction est conçue pour être observée par le ViewModel. Grâce à Flow,
     * toute modification dans la table des films de la base de données (via getAllMovies())
     * déclenchera automatiquement une nouvelle émission de la liste mise à jour
     * des MovieUiModel.
     *
     * @return Un Flow qui émet une liste de MovieUiModel chaque fois que les données
     * sous-jacentes dans la base de données changent.
     */
    override fun getMovies(): Flow<List<MovieUiModel>> =
    // Définit la fonction qui retourne un Flow de List<MovieUiModel>
    // 1. Appelle la fonction du DAO (localDataSource) qui retourne un Flow<List<MovieEntity>>.
        //    Ce Flow émettra une nouvelle liste d'entités chaque fois que la table "movies" change.
        localDataSource.getAllMovies()
            // 2. Utilise l'opérateur `map` de Flow. Cet opérateur s'applique à chaque émission
            //    du Flow source (c'est-à-dire chaque List<MovieEntity> émise par getAllMovies()).
            .map { movieEntities: List<MovieEntity> -> // Le lambda reçoit la liste d'entités actuelle
                // 3. Utilise l'opérateur `map` des collections Kotlin sur la liste d'entités reçue.
                //    Ceci transforme chaque MovieEntity individuelle dans la liste...
                movieEntities.map { movieEntity -> // Le lambda reçoit une MovieEntity individuelle
                    // 4. ...en appelant la fonction de conversion `toUiModel()` sur cette entité.
                    //    `toUiModel()` (que nous avons définie ailleurs) transforme l'entité DB
                    //    en modèle UI prêt à être affiché.
                    movieEntity.toUiModel() // Résultat: un MovieUiModel
                } // Résultat de ce map interne: une List<MovieUiModel>
            } // Résultat de l'opérateur map de Flow: un Flow<List<MovieUiModel>>
}
