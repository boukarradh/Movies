package com.epsi.movies.domain.repository

import com.epsi.movies.data.network.MovieListResponseNetwork
import com.epsi.movies.domain.model.movies.MovieDetailsUiModel
import com.epsi.movies.domain.model.movies.MovieUiModel
import kotlinx.coroutines.flow.Flow

/**
 * Interface définissant les opérations possibles pour récupérer les données des films.
 * Sert de contrat pour l'implémentation et de point d'accès pour les ViewModels.
 */
interface MovieRepository {
    /**
     * Récupère la liste des films populaires depuis la source de données.
     * @param page La page à récupérer.
     * @return La réponse contenant la liste des films ou null en cas d'erreur.
     */
    suspend fun fetchPopularMovies(page: Int = 1): Boolean

    /**
     * Recherche des films par mot-clé.
     * @param query Le terme de recherche.
     * @param page La page à récupérer.
     * @return La réponse contenant la liste des films trouvés ou null en cas d'erreur.
     */
    suspend fun searchMovies(query: String, page: Int = 1): MovieListResponseNetwork?

    /**
     * Récupère les détails d'un film spécifique.
     * @param movieId L'ID du film.
     * @return Les détails du film ou null en cas d'erreur.
     */
    suspend fun getMovieDetails(movieId: Int): MovieDetailsUiModel?

    /**
     * Récupère la liste des films sous format d Flow.
     * @return Un Flow qui émet une liste de MovieUiModel chaque fois que les données sous-jacentes dans la base de données changent.
     */
    fun getMovies(): Flow<List<MovieUiModel>>
}