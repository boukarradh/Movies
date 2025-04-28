package com.epsi.movies.data.db

import com.epsi.movies.data.db.dao.MovieDao
import com.epsi.movies.data.db.entities.MovieEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import com.epsi.movies.data.db.dao.UserDao
import com.epsi.movies.data.db.entities.UserEntity


/**
 * Classe abstraite représentant la base de données Room de l'application.
 * Elle liste les entités (tables) et fournit l'accès aux DAOs.
 */
@Database(
    entities = [
        MovieEntity::class, // Table des films
        UserEntity::class   // Ajout de l'entité User à la liste des tables
        // Ajoutez d'autres entités ici, séparées par des virgules
        // WatchlistEntity::class,
    ],
    version = 2, // Gardez la version 1 pour l'instant, incrémenter si le schéma change.
    exportSchema = true // Exporter le schéma dans un fichier (bonne pratique pour les migrations)
    // autoMigrations = [] // Pour gérer les migrations automatiquement si besoin plus tard
)
abstract class AppDatabase : RoomDatabase() { // Renommé en AppDatabase par convention

    /**
     * Fournit une instance du DAO pour interagir avec la table des films.
     * Room générera l'implémentation de cette méthode.
     */
    abstract fun movieDao(): MovieDao

    /**
     * Fournit une instance du DAO pour interagir avec la table des utilisateurs.
     * Room générera l'implémentation de cette méthode.
     */
    abstract fun userDao(): UserDao // Ajout de la méthode abstraite pour UserDao

}
