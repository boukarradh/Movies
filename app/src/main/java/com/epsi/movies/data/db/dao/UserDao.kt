package com.epsi.movies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epsi.movies.data.db.entities.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) pour l'entité UserEntity.
 * Définit les méthodes pour interagir avec la table "users" dans la base de données Room,
 * principalement pour l'authentification locale.
 */
@Dao
interface UserDao {

    /**
     * Insère un nouvel utilisateur dans la base de données.
     * La stratégie de conflit ABORT (par défaut si non spécifiée, mais explicite ici)
     * signifie que si un utilisateur avec le même 'username' (à cause de l'index unique)
     * existe déjà, l'insertion échouera et lèvera une exception (ex: SQLiteConstraintException).
     * C'est le comportement souhaité pour l'inscription : on ne peut pas s'inscrire
     * avec un nom d'utilisateur déjà pris.
     *
     * @param user L'entité UserEntity à insérer.
     * @return L'ID (rowId) de l'utilisateur nouvellement inséré, ou -1 en cas d'échec/conflit.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long // Retourne Long pour l'ID généré

    /**
     * Recherche un utilisateur dans la base de données par son nom d'utilisateur.
     * Utilisé pour vérifier si un nom d'utilisateur existe déjà lors de l'inscription,
     * ou pour récupérer les informations de l'utilisateur lors de la connexion.
     *
     * @param username Le nom d'utilisateur à rechercher.
     * @return L'entité UserEntity correspondante si trouvée, ou null sinon.
     */
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    /**
     * Observe le premier (et potentiellement unique) utilisateur dans la table.
     * Utile pour récupérer les informations de l'utilisateur enregistré dans le scénario simplifié.
     * Le Flow émettra une nouvelle valeur (l'entité ou null) si l'utilisateur est ajouté ou supprimé.
     *
     * @return Un Flow émettant l'entité UserEntity du premier utilisateur trouvé, ou null si la table est vide.
     */
    @Query("SELECT * FROM users LIMIT 1") // Sélectionne le premier utilisateur trouvé
    fun getFirstUser(): Flow<UserEntity?> // Retourne Flow<UserEntity?>, 'suspend' est retiré

    /**
     * Observe s'il existe au moins un utilisateur dans la table.
     * Utile pour déterminer si l'écran d'inscription ou de connexion doit être affiché au démarrage.
     * Le Flow émettra une nouvelle valeur (true ou false) chaque fois que le contenu
     * de la table (affectant le résultat du COUNT) change.
     *
     * @return Un Flow émettant `true` s'il y a au moins un utilisateur, `false` si la table est vide.
     */
    @Query("SELECT COUNT(*) > 0 FROM users") // Compte les lignes et vérifie si le compte est > 0
    fun hasUser(): Flow<Boolean> // Retourne maintenant Flow<Boolean>,

    /**
     * Supprime tous les utilisateurs de la table.
     * Utile pour la fonction "logout" dans le scénario simplifié.
     */
    @Query("DELETE FROM users")
    suspend fun clearAllUsers()

}