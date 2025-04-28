package com.epsi.movies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epsi.movies.data.db.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) pour l'entité MovieEntity.
 * Définit les méthodes pour interagir avec la table "movies" dans la base de données Room.
 */
@Dao
interface MovieDao {

    /**
     * Insère une liste de films dans la base de données.
     * Si un film avec le même ID existe déjà, il sera remplacé grâce à la stratégie OnConflictStrategy.REPLACE.
     * C'est utile pour mettre à jour les données du cache.
     *
     * @param movies La liste des entités MovieEntity à insérer ou remplacer.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    /**
     * Récupère tous les films stockés dans la base de données, ordonnés par popularité décroissante (par exemple).
     * Utilise Flow pour émettre automatiquement une nouvelle liste lorsque les données de la table changent.
     *
     * @return Un Flow émettant la liste de tous les MovieEntity.
     */
    @Query("SELECT * FROM movies ORDER BY popularity DESC") // Exemple de tri
    fun getAllMovies(): Flow<List<MovieEntity>>
    // Alternative sans Flow (si vous ne voulez pas d'observation réactive):
    // @Query("SELECT * FROM movies ORDER BY popularity DESC")
    // suspend fun getAllMoviesList(): List<MovieEntity>


    /**
     * Récupère un film spécifique par son ID.
     * Utilise Flow pour observer les changements sur ce film spécifique.
     *
     * @param movieId L'ID du film à récupérer.
     * @return Un Flow émettant le MovieEntity correspondant ou null s'il n'est pas trouvé.
     */
    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity?>
    // Alternative sans Flow:
    // @Query("SELECT * FROM movies WHERE id = :movieId")
    // suspend fun findMovieById(movieId: Int): MovieEntity?

    /**
     * Supprime tous les films de la table.
     * Utile pour vider complètement le cache.
     */
    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()

    /**
     * Récupère le film le plus récemment ajouté (basé sur fetchedAt).
     * Utile pour vérifier l'âge du cache.
     *
     * @return Le MovieEntity le plus récent ou null si la table est vide.
     */
    @Query("SELECT * FROM movies ORDER BY fetched_at DESC LIMIT 1")
    suspend fun getLatestMovie(): MovieEntity?



}
