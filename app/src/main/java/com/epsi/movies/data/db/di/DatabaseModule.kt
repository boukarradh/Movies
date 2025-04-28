package com.epsi.movies.data.db.di

import android.content.Context
import androidx.room.Room
import com.epsi.movies.common.Common.DATA_BASE_NAME
import com.epsi.movies.data.db.AppDatabase
import com.epsi.movies.data.db.dao.MovieDao
import com.epsi.movies.data.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Annote cet objet comme un module Hilt, indiquant qu'il fournit des dépendances.
@InstallIn(SingletonComponent::class) // Spécifie que les dépendances fournies par ce module auront une portée Singleton,
// c'est-à-dire qu'une seule instance sera créée et partagée pour toute la durée de vie de l'application.


object DatabaseModule {

    /**
     * Fournit une instance de UserDao.
     * Hilt utilisera cette fonction lorsqu'une dépendance de type UserDao est requise.
     *
     * @param database L'instance de AppDatabase (la base de données Room). Hilt sait comment
     * fournir cette instance grâce à la fonction provideAppDatabase.
     * @return Une instance de UserDao obtenue depuis la base de données.
     */
    @Provides
    fun providesUserDao( // Nom de fonction adapté
        database: AppDatabase // Hilt injecte l'instance de la base de données ici.
    ): UserDao = database.userDao() // Appelle la méthode abstraite userDao() définie dans votre classe AppDatabase.

    /**
     * Fournit une instance de MovieDao.
     * Hilt utilisera cette fonction lorsqu'une dépendance de type MovieDao est requise.
     *
     * @param database L'instance de AppDatabase (la base de données Room). Hilt sait comment
     * fournir cette instance grâce à la fonction provideAppDatabase ci-dessous.
     * @return Une instance de MovieDao obtenue depuis la base de données.
     */
    @Provides
    fun providesMoviesDao(
        database: AppDatabase // Hilt injecte l'instance de la base de données ici.
    ): MovieDao = database.movieDao() // Appelle la méthode abstraite définie dans votre classe AppDatabase.

    /**
     * Fournit l'instance unique (Singleton) de la base de données Room (AppDatabase).
     * Hilt utilisera cette fonction pour créer et fournir l'instance de la base de données
     * lorsque c'est nécessaire (par exemple, pour injecter dans providesMoviesDao).
     *
     * @param appContext Le contexte de l'application, injecté par Hilt grâce à @ApplicationContext.
     * Nécessaire pour créer la base de données Room.
     * @return L'instance unique de AppDatabase.
     */
    @Provides
    @Singleton // Assure qu'une seule instance de la base de données sera créée et réutilisée.
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder( // Utilise le builder fourni par la bibliothèque Room.
            appContext, // Contexte nécessaire pour accéder au système de fichiers de l'application.
            AppDatabase::class.java, // La classe (annotée @Database) qui définit votre base de données.
            DATA_BASE_NAME // Le nom du fichier qui sera créé sur l'appareil pour stocker la base de données.
        )
            // Stratégie de migration utilisée si la version de la base de données (@Database(version=...)) est incrémentée
            // et qu'aucune règle de migration explicite n'est fournie.
            .fallbackToDestructiveMigration()
            // Construit et retourne l'instance de la base de données configurée.
            .build()
    }
}
