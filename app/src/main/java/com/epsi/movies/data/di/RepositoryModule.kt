package com.epsi.movies.data.di

import com.epsi.movies.data.db.dao.MovieDao
import com.epsi.movies.data.db.dao.UserDao
import com.epsi.movies.data.repository.MovieRepositoryImpl
import com.epsi.movies.data.repository.UserRepositoryImpl
import com.epsi.movies.domain.repository.MovieRepository
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
    fun provideMovieRepositoryImpl(dao :MovieDao) : MovieRepository =
        MovieRepositoryImpl(localDataSource = dao)

    @Singleton
    @Provides
    fun provideUserRepositoryImpl(dao : UserDao) : UserRepository =
        UserRepositoryImpl(localDataSource = dao)
}