package com.epsi.movies.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epsi.movies.data.db.dao.MovieDao
import com.epsi.movies.data.db.dao.UserDao
import com.epsi.movies.data.repository.MovieRepositoryImpl
import com.epsi.movies.data.repository.UserPreferencesRepositoryImpl
import com.epsi.movies.data.repository.UserRepositoryImpl
import com.epsi.movies.domain.repository.MovieRepository
import com.epsi.movies.domain.repository.UserPreferencesRepository
import com.epsi.movies.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepositoryImpl(dao: MovieDao): MovieRepository =
        MovieRepositoryImpl(localDataSource = dao)

    @Singleton
    @Provides
    fun provideUserRepositoryImpl(dao: UserDao): UserRepository =
        UserRepositoryImpl(localDataSource = dao)

    @Singleton
    @Provides
    fun provideUserPreferencesRepositoryImpl(dataStore: DataStore<Preferences>): UserPreferencesRepository =
        UserPreferencesRepositoryImpl(dataStore = dataStore)
}