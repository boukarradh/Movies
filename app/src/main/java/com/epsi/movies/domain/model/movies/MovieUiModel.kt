package com.epsi.movies.domain.model.movies

/**
 * Représente un film tel qu'il doit être affiché dans l'interface utilisateur (UI).
 * Ce modèle est découplé des détails de l'API ou de la base de données.
 * Il contient les informations formatées et prêtes à être affichées,
 * ainsi que les données brutes si nécessaire.
 */
data class MovieUiModel(
    // --- Champs principaux (identiques à l'entité) ---
    val id: Int,
    val title: String,
    val overview: String, // Non-nullable ici, valeur par défaut fournie dans le mapping
    val posterPath: String?, // Chemin brut (peut être utile)
    val backdropPath: String?, // Chemin brut (peut être utile)
    val releaseDate: String?, // Date brute (peut être utile)
    val voteAverage: Double?, // Note brute (peut être utile)
    val voteCount: Int?, // Nombre de votes brut
    val originalLanguage: String?,
    val originalTitle: String?,
    val popularity: Double?,
    val fetchedAt: Long, // Timestamp de récupération
    val posterUrl: String?, // URL complète de l'affiche
    val backdropUrl: String?, // URL complète du backdrop
    val releaseDateFormatted: String, // Date formatée
    val voteAverageFormatted: String, // Note formatée
    val cacheAgeDescription: String,// Description de l'âge du cache
    val isFavorite: Boolean = false,
)