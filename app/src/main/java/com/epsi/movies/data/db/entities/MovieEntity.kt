package com.epsi.movies.data.db.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Représente un film dans la base de données locale Room.
 * Utilisé pour mettre en cache les informations des films récupérées depuis l'API.
 *
 * @param id Identifiant unique TMDB du film, utilisé comme clé primaire.
 * @param title Titre du film.
 * @param overview Synopsis/résumé du film.
 * @param posterPath Chemin relatif de l'affiche (peut être null).
 * @param backdropPath Chemin relatif de l'image de fond (peut être null).
 * @param releaseDate Date de sortie (format YYYY-MM-DD, peut être null).
 * @param voteAverage Note moyenne (sur 10, peut être null).
 * @param voteCount Nombre de votes (peut être null).
 * @param originalLanguage Langue originale.
 * @param originalTitle Titre original.
 * @param popularity Score de popularité.
 * @param fetchedAt Timestamp indiquant quand cette donnée a été récupérée (utile pour la stratégie de cache).
 */
@Entity(tableName = "movies") // Définit le nom de la table dans la base de données
data class MovieEntity(

    @PrimaryKey // Marque ce champ comme clé primaire unique
    @ColumnInfo(name = "id") // Définit le nom de la colonne (optionnel si identique au nom de la variable)
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    @ColumnInfo(name = "release_date")
    val releaseDate: String?,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int?,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = null,

    @ColumnInfo(name = "original_title")
    val originalTitle: String? = null,

    @ColumnInfo(name = "popularity")
    val popularity: Double? = null,

    // --- Champ pour la gestion du cache ---
    @ColumnInfo(name = "fetched_at", defaultValue = "0") // Timestamp en millisecondes
    val fetchedAt: Long = System.currentTimeMillis()

)



