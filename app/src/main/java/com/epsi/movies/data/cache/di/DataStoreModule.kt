package com.epsi.movies.data.cache.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Définit le nom du fichier qui sera utilisé par Preferences DataStore.
// Il est privé à ce module car seul le module a besoin de le connaître pour créer le DataStore.
private const val USER_PREFERENCES_NAME = "user_preferences" // Renommé pour clarté

// Définit une extension de propriété sur Context en utilisant le delegate 'preferencesDataStore'.
// Ce delegate garantit qu'une seule instance de DataStore<Preferences> est créée
// pour un nom donné ("user_preferences") au sein d'un contexte donné (ici, le contexte de l'application).
// Rendre cette extension privée garantit qu'elle n'est utilisée qu'à l'intérieur de ce module
// pour la création de l'instance via Hilt.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

/**
 * Module Hilt responsable de fournir l'instance de DataStore<Preferences>
 * pour gérer les préférences utilisateur de l'application.
 */
@Module // Annote cet objet comme un module Hilt.
@InstallIn(SingletonComponent::class) // Indique que les dépendances fournies ici auront une portée Singleton (liée à l'application).
object DataStoreModule {

    /**
     * Fournit l'instance unique (Singleton) de DataStore<Preferences>.
     * Hilt appellera cette fonction lorsqu'une dépendance de type DataStore<Preferences>
     * est requise (par exemple, pour l'injecter dans UserPreferencesRepository).
     *
     * @param appContext Le contexte global de l'application, injecté par Hilt
     * grâce à l'annotation @ApplicationContext. Nécessaire pour accéder au DataStore
     * via l'extension de propriété 'dataStore'.
     * @return L'instance singleton de DataStore<Preferences> pour l'application.
     */
    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
    }
