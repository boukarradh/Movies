package com.epsi.movies.data.repository

import android.util.Log
import com.epsi.movies.common.Common.LOG_TAG
import com.epsi.movies.data.converter.toUiModel
import com.epsi.movies.data.db.dao.UserDao
import com.epsi.movies.data.db.entities.UserEntity
import com.epsi.movies.domain.model.user.UserUiModel
import com.epsi.movies.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Implémentation concrète et simplifiée de UserRepository pour la gestion d'un compte utilisateur unique.
 * Utilise UserDao pour interagir avec la base de données Room.
 * Les fonctions de vérification d'état (existence, nom) sont suspendues pour une lecture unique.
 *
 * @param localDataSource Le DAO pour accéder à la table des utilisateurs.
 */
class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserDao
) : UserRepository {

    /**
     * Enregistre l'utilisateur unique.
     * Lève une exception si le nom d'utilisateur est déjà pris (géré par le DAO et l'index unique).
     */
    override suspend fun registerUser(username: String, password: String): Long {
        return withContext(Dispatchers.IO) {
            try {
                val newUser = UserEntity(username = username, password = password)
                val userId = localDataSource.insertUser(newUser)
                userId
            } catch (e: Exception) {
                Log.e(
                    LOG_TAG,
                    "Erreur lors de l'enregistrement de l'utilisateur $username: ${e.message}",
                    e
                )
                throw e // Relance l'exception
            }
        }
    }

    /**
     * Vérifie les identifiants de connexion.
     */
    override suspend fun loginUser(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(LOG_TAG, "Tentative de connexion pour l'utilisateur: $username")
                val user = localDataSource.getUserByUsername(username)
                user != null && user.password == password

            } catch (e: Exception) {
                Log.e(
                    LOG_TAG,
                    "Erreur lors de la tentative de connexion pour $username: ${e.message}",
                    e
                )
                false
            }
        }
    }


    override fun doesUserExist(): Flow<Boolean> {
        return localDataSource.hasUser()
    }

    override fun getRegisteredUser(): Flow<UserUiModel?> {
        return localDataSource.getFirstUser().map { it?.toUiModel() }
    }


    /**
     * Supprime toutes les données utilisateur.
     * Nécessite une méthode `clearAllUsers()` dans le UserDao.
     */
    override suspend fun clearUserData() {
        withContext(Dispatchers.IO) {
            try {
                localDataSource.clearAllUsers()
            } catch (e: Exception) {
                Log.e(
                    LOG_TAG,
                    "Erreur lors de la suppression des données utilisateur: ${e.message}",
                    e
                )
            }
        }
    }
}