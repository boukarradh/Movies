package com.epsi.movies.data.network


import com.epsi.movies.common.Common.LANGUAGE_PARAM
import com.epsi.movies.common.Common.PAGE_PARAM
import com.epsi.movies.common.Common.QUERY_PARAM
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface définissant les points d'accès (endpoints) de l'API TheMovieDB (TMDB)
 * pour être utilisée avec Retrofit.
 */
interface MoviesApiService {


    /**
     * Récupère la liste des films populaires.
     * @param language La langue des résultats (ex: "fr-FR", "en-US").
     * @param page Le numéro de la page à récupérer.
     * @return Une réponse contenant la liste des films populaires.
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(LANGUAGE_PARAM) language: String = "fr-FR",
        @Query(PAGE_PARAM) page: Int = 1
        // La clé API sera ajoutée par un Interceptor (voir NetworkModule)
        // @Query(API_KEY_PARAM) apiKey: String = API_KEY_VALUE
    ): MovieListResponseNetwork

    /**
     * Recherche des films par mot-clé.
     * @param query Le terme de recherche.
     * @param language La langue des résultats.
     * @param page Le numéro de la page à récupérer.
     * @return Une réponse contenant la liste des films correspondants.
     */
    @GET("search/movie")
    suspend fun searchMovies(
        @Query(QUERY_PARAM) query: String,
        @Query(LANGUAGE_PARAM) language: String = "fr-FR",
        @Query(PAGE_PARAM) page: Int = 1
        // La clé API sera ajoutée par un Interceptor
        // @Query(API_KEY_PARAM) apiKey: String = API_KEY_VALUE
    ): MovieListResponseNetwork

    /**
     * Récupère les détails complets d'un film spécifique par son ID.
     * @param movieId L'ID du film TMDB.
     * @param language La langue des résultats.
     * @return Les détails du film.
     */
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query(LANGUAGE_PARAM) language: String = "fr-FR"
        // La clé API sera ajoutée par un Interceptor
        // @Query(API_KEY_PARAM) apiKey: String = API_KEY_VALUE
    ): MovieDetailsResponseNetwork



}
