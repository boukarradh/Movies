package com.epsi.movies.data.network

import com.google.gson.annotations.SerializedName

/**
 * Représente la structure de la réponse JSON de l'API TMDB
 * pour les détails complets d'un film spécifique (endpoint /movie/{movie_id}).
 */
data class MovieDetailsResponseNetwork(

    @SerializedName("backdrop_path")
    val backdropPath: String?, // Chemin relatif de l'image de fond

    // belongs_to_collection: Any?, // Peut être un objet complexe, omis pour la simplicité

    @SerializedName("budget")
    val budget: Long?, // Budget du film

    @SerializedName("genres")
    val genres: List<ApiGenre>?, // Liste des objets Genre associés

    @SerializedName("homepage")
    val homepage: String?, // URL de la page d'accueil du film

    @SerializedName("id")
    val id: Int, // Identifiant unique TMDB

    @SerializedName("imdb_id")
    val imdbId: String?, // Identifiant IMDb

    @SerializedName("origin_country")
    val originCountry: List<String>?, // Pays d'origine

    @SerializedName("original_language")
    val originalLanguage: String?, // Langue originale

    @SerializedName("original_title")
    val originalTitle: String?, // Titre original

    @SerializedName("overview")
    val overview: String?, // Synopsis/résumé

    @SerializedName("popularity")
    val popularity: Double?, // Score de popularité

    @SerializedName("poster_path")
    val posterPath: String?, // Chemin relatif de l'affiche

    // production_companies: List<ProductionCompany>?, // Omis pour la simplicité
    // production_countries: List<ProductionCountry>?, // Omis pour la simplicité

    @SerializedName("release_date")
    val releaseDate: String?, // Date de sortie (YYYY-MM-DD)

    @SerializedName("revenue")
    val revenue: Long?, // Recettes du film

    @SerializedName("runtime")
    val runtime: Int?, // Durée en minutes

    // spoken_languages: List<SpokenLanguage>?, // Omis pour la simplicité

    @SerializedName("status")
    val status: String?, // Statut (ex: "Released", "Post Production")

    @SerializedName("tagline")
    val tagline: String?, // Slogan/accroche du film

    @SerializedName("title")
    val title: String, // Titre (localisé si possible)

    @SerializedName("video")
    val video: Boolean?, // Si une vidéo est associée

    @SerializedName("vote_average")
    val voteAverage: Double?, // Note moyenne

    @SerializedName("vote_count")
    val voteCount: Int? // Nombre de votes
)

/**
 * Représente un genre tel que retourné dans la liste "genres"
 * de la réponse MovieDetailsResponse.
 */
data class ApiGenre(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)