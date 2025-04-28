package com.epsi.movies.domain.model.movies

/**
* Représente les détails d'un film formatés et prêts à être affichés
* dans l'interface utilisateur (UI), tout en conservant les données brutes
* reçues de MovieDetailsResponseNetwork.
*/
data class MovieDetailsUiModel(
    // --- Champs Bruts (correspondant à MovieDetailsResponseNetwork) ---
    val id: Int,
    val backdropPath: String?, // Chemin brut
    val budget: Long?,
    // Note: La liste brute des genres (List<ApiGenre>?) n'est pas incluse ici
    // pour garder le modèle UI simple. Le champ 'genres' formaté est fourni.
    val homepage: String?,
    val imdbId: String?,
    val originCountry: List<String>?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?, // Nullable ici, le mapper peut fournir une valeur par défaut si besoin
    val popularity: Double?,
    val posterPath: String?, // Chemin brut
    val releaseDate: String?, // Date brute (YYYY-MM-DD)
    val revenue: Long?,
    val runtime: Int?, // Durée brute en minutes
    val status: String?,
    val tagline: String?, // Peut être null ou vide
    val title: String,
    val video: Boolean?,
    val voteAverage: Double?, // Note brute
    val voteCount: Int?, // Nombre de votes brut

    // --- Champs Formatés/Dérivés pour l'UI ---
    val posterUrl: String?, // URL complète de l'affiche
    val backdropUrl: String?, // URL complète du backdrop
    val releaseDateFormatted: String, // Date formatée (ex: "28 avril 2025")
    val voteAverageFormatted: String, // Note formatée (ex: "7.5/10")
    val runtimeFormatted: String, // Durée formatée (ex: "2h 15min")
    val genres: String // Liste des noms de genres concaténés (ex: "Action, Aventure")
    // Ajoutez d'autres champs formatés si nécessaire (ex: budgetFormatted, revenueFormatted)
)
