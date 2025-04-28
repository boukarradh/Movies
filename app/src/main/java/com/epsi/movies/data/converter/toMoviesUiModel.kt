package com.epsi.movies.data.converter

import com.epsi.movies.data.db.entities.MovieEntity
import com.epsi.movies.data.network.ApiGenre
import com.epsi.movies.data.network.MovieDetailsResponseNetwork
import com.epsi.movies.domain.model.movies.MovieDetailsUiModel
import com.epsi.movies.domain.model.movies.MovieUiModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

fun MovieEntity.toUiModel(): MovieUiModel {
    return MovieUiModel(
        // Champs bruts (directement depuis l'entité)
        id = this.id,
        title = this.title,
        overview = this.overview ?: "Pas de description disponible.", // Valeur par défaut si null
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        popularity = this.popularity,
        fetchedAt = this.fetchedAt, // Utilise le timestamp stocké
        posterUrl = buildImageUrl(this.posterPath, "w500"),
        backdropUrl = buildImageUrl(this.backdropPath, "w780"),
        releaseDateFormatted = formatReleaseDate(this.releaseDate),
        voteAverageFormatted = formatVoteAverage(this.voteAverage),
        cacheAgeDescription = getCacheAgeDescription(this.fetchedAt) // Description basée sur le timestamp stocké
    )
}

/**
 * Convertit un objet MovieDetailsResponseNetwork (détails du réseau) en MovieUiModel (pour l'UI).
 * Cette fonction doit être appelée (par exemple dans le ViewModel ou le Repository)
 * après avoir reçu la réponse de l'API pour les détails d'un film.
 *
 * @receiver L'objet MovieDetailsResponseNetwork source.
 * @return L'objet MovieUiModel prêt à être affiché.
 */
fun MovieDetailsResponseNetwork.toUiModel(): MovieDetailsUiModel {
    return MovieDetailsUiModel(
        // Champs Bruts
        id = this.id,
        backdropPath = this.backdropPath,
        budget = this.budget,
        homepage = this.homepage,
        imdbId = this.imdbId,
        originCountry = this.originCountry,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview, // Conserve la nullabilité ici
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,

        // Champs Formatés
        posterUrl = buildImageUrl(this.posterPath, "w500"),
        backdropUrl = buildImageUrl(this.backdropPath, "w780"),
        releaseDateFormatted = formatReleaseDate(this.releaseDate),
        voteAverageFormatted = formatVoteAverage(this.voteAverage),
        runtimeFormatted = formatRuntime(this.runtime),
        genres = formatGenres(this.genres) // Appelle la fonction de formatage des genres
    )
}
/**
 * Formate la durée du film pour l'affichage.
 * @param runtimeMinutes Durée en minutes ou null.
 * @return Durée formatée (ex: "2h 15min") ou "Durée inconnue".
 */
private fun formatRuntime(runtimeMinutes: Int?): String {
    return runtimeMinutes?.takeIf { it > 0 }?.let {
        val hours = it / 60
        val minutes = it % 60
        if (hours > 0) "${hours}h ${minutes}min" else "${minutes}min"
    } ?: "Durée inconnue"
}

/**
 * Concatène les noms des genres en une seule chaîne.
 * @param genres La liste des objets ApiGenre ou null.
 * @return Une chaîne contenant les noms des genres séparés par ", " ou "Genres inconnus".
 */
// Rendu public ou interne pour être utilisé par le mapper externe si besoin,
// ou garder privé si le mapper est dans le même fichier.
// Assurez-vous que ApiGenre est importé.
internal fun formatGenres(genres: List<ApiGenre>?): String {
    return genres?.takeIf { it.isNotEmpty() }
        ?.joinToString(separator = ", ") { it.name } ?: "Genres inconnus"
}


/** Format pour parser la date de l'API TMDB */
private val tmdbDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
/** Format pour afficher la date dans l'UI */
private val displayDateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

/**
 * Donne une description de l'âge des données mises en cache.
 * @param fetchedAt Timestamp (en ms) de récupération.
 * @return Une description textuelle (ex: "à l'instant", "il y a 5 min", "hier").
 */
private fun getCacheAgeDescription(fetchedAt: Long): String {
    if (fetchedAt <= 0) return "Jamais mis à jour"
    val now = System.currentTimeMillis()
    val diff = now - fetchedAt
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)

    return when {
        minutes < 1 -> "à l'instant"
        minutes < 60 -> "il y a $minutes min"
        hours < 24 -> "il y a $hours h"
        days == 1L -> "hier"
        else -> "il y a $days jours"
    }
}

/**
 * Formate la note moyenne pour l'affichage.
 * @param voteAverage La note moyenne ou null.
 * @return La note formatée (ex: "7.5/10") ou "N/A".
 */
private fun formatVoteAverage(voteAverage: Double?): String {
    return voteAverage?.takeIf { it > 0 }?.let { String.format(Locale.US, "%.1f/10", it) } ?: "N/A"
}

/**
 * Formate la date de sortie pour l'affichage.
 * @param releaseDate La date au format "yyyy-MM-dd" ou null.
 * @return La date formatée ou "N/A".
 */
private fun formatReleaseDate(releaseDate: String?): String {
    return try {
        releaseDate?.takeIf { it.isNotBlank() }?.let {
            val date = tmdbDateFormat.parse(it)
            date?.let { d -> displayDateFormat.format(d) } ?: "N/A"
        } ?: "N/A"
    } catch (e: Exception) {
        "N/A" // En cas d'erreur de parsing
    }
}

/**
 * Construit l'URL complète pour une image TMDB à partir d'un chemin relatif.
 * @param path Le chemin relatif (ex: /xyz.jpg).
 * @param size La taille souhaitée (ex: "w500", "w780", "original").
 * @return L'URL complète ou null si le chemin est null ou vide.
 */
private fun buildImageUrl(path: String?, size: String): String? {
    return path?.takeIf { it.isNotBlank() }?.let { "https://image.tmdb.org/t/p/$size$it" }
}