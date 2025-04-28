package com.epsi.movies.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Représente un utilisateur dans la base de données locale Room.
 * Utilisé pour le système d'authentification local simple.
 *
 * @param userId Identifiant unique auto-généré pour l'utilisateur (clé primaire).
 * @param username Nom d'utilisateur choisi par l'utilisateur (doit être unique).
 * @param password Le mot de passe de l'utilisateur, stocké en clair pour la simplicité de ce TP.
 * **ATTENTION : Ne jamais stocker de mots de passe en clair en production !**
 * C'est une pratique dangereuse pour la sécurité. Une vraie application
 * utiliserait un hash salé et sécurisé (ex: avec bcrypt).
 */
@Entity(
    tableName = "users", // Nom de la table dans la base de données
    // Ajoute un index sur la colonne 'username' pour garantir son unicité
    // et accélérer les recherches par nom d'utilisateur.
    indices = [Index(value = ["username"], unique = true)]
)
data class UserEntity(

    @PrimaryKey(autoGenerate = true) // Clé primaire auto-incrémentée
    @ColumnInfo(name = "user_id")
    val userId: Long = 0, // Utiliser Long pour l'auto-génération, 0 est la valeur par défaut pour Room

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "password")
    val password: String // Stocke le mot de passe en clair
)