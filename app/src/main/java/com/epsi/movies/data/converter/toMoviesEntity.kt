package com.epsi.movies.data.converter

import com.epsi.movies.data.db.entities.MovieEntity
import com.epsi.movies.data.network.ApiMovie

/**
 * Convertit un objet ApiMovie (provenant du réseau/API) en un objet MovieEntity
 * (destiné à être stocké dans la base de données Room).
 *
 * @receiver L'objet ApiMovie source.
 * @return L'objet MovieEntity correspondant.
 */
fun ApiMovie.toEntity(): MovieEntity {
    // Crée une nouvelle instance de MovieEntity en copiant les champs pertinents
    // depuis l'objet ApiMovie.
    return MovieEntity(
        id = this.id, // L'ID est la clé primaire
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        popularity = this.popularity,
        fetchedAt = System.currentTimeMillis() // Enregistre le moment où la donnée est convertie/récupérée
    )
}

/**
 * Convertit une liste d'objets ApiMovie en une liste d'objets MovieEntity.
 * Utile pour traiter les réponses de l'API contenant plusieurs films.
 *
 * @receiver La liste d'ApiMovie source.
 * @return La liste de MovieEntity correspondante.
 */
fun List<ApiMovie>.toEntityList(): List<MovieEntity> {
    // Applique la fonction de conversion .toEntity() à chaque élément de la liste (chaque film).
    return this.map { apiMovie -> apiMovie.toEntity() }
}
