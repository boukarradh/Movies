package com.epsi.movies.data.network

import com.google.gson.annotations.SerializedName

/**
 * Représente la structure complète de la réponse JSON de l'API TMDB
 * pour une requête de liste de films (ex: populaires, recherche).
 */
data class MovieListResponse(
    @SerializedName("page")
    val page: Int, // Le numéro de la page actuelle

    @SerializedName("results")
    val results: List<ApiMovie>, // La liste des films pour cette page

    @SerializedName("total_pages")
    val totalPages: Int, // Le nombre total de pages disponibles

    @SerializedName("total_results")
    val totalResults: Int // Le nombre total de résultats sur toutes les pages
)

/**
 * Représente un seul film tel que défini dans la liste "results" de la réponse TMDB.
 * Contient les informations principales d'un film.
 */
data class ApiMovie(
    @SerializedName("adult")
    val adult: Boolean, // Indique si le film est pour adultes

    @SerializedName("backdrop_path")
    val backdropPath: String?, // Chemin relatif de l'image de fond (peut être null)

    @SerializedName("genre_ids")
    val genreIds: List<Int>?, // Liste des IDs des genres (peut être null)

    @SerializedName("id")
    val id: Int, // Identifiant unique TMDB du film

    @SerializedName("original_language")
    val originalLanguage: String?, // Langue originale du titre

    @SerializedName("original_title")
    val originalTitle: String?, // Titre original

    @SerializedName("overview")
    val overview: String?, // Synopsis/résumé du film (peut être vide ou null)

    @SerializedName("popularity")
    val popularity: Double?, // Score de popularité

    @SerializedName("poster_path")
    val posterPath: String?, // Chemin relatif de l'affiche (peut être null)

    @SerializedName("release_date")
    val releaseDate: String?, // Date de sortie (format YYYY-MM-DD, peut être null)

    @SerializedName("title")
    val title: String, // Titre du film (localisé si possible)

    @SerializedName("video")
    val video: Boolean, // Indique si une vidéo est associée (généralement false pour les films)

    @SerializedName("vote_average")
    val voteAverage: Double?, // Note moyenne (sur 10)

    @SerializedName("vote_count")
    val voteCount: Int? // Nombre de votes
)