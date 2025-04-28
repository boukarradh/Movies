package com.epsi.movies.domain.repository

import com.epsi.movies.domain.model.user.UserUiModel
import kotlinx.coroutines.flow.Flow



/**
 * Interface définissant les opérations possibles pour la gestion d'un compte utilisateur unique,
 * pour un système d'authentification local simplifié.
 * Sert de contrat pour l'implémentation et de point d'accès pour les ViewModels.
 */
interface UserRepository {

    // --- Gestion du Compte Utilisateur Unique ---

    /**
     * Enregistre le compte utilisateur unique de l'application.
     * L'implémentation doit s'assurer qu'un seul utilisateur peut être enregistré
     * (par exemple, en vidant la table avant l'insertion ou en gérant les conflits).
     *
     * @param username Le nom d'utilisateur choisi.
     * @param password Le mot de passe choisi (en clair pour ce TP).
     * @return Long : L'ID du nouvel utilisateur inséré en cas de succès.
     * L'implémentation devra gérer les exceptions potentielles.
     */
    suspend fun registerUser(username: String, password: String): Long

    /**
     * Vérifie si les identifiants fournis correspondent à l'utilisateur enregistré.
     *
     * @param username Le nom d'utilisateur fourni.
     * @param password Le mot de passe fourni.
     * @return Boolean : true si les identifiants correspondent à l'utilisateur enregistré,
     * false sinon (utilisateur non trouvé ou mot de passe incorrect).
     */
    suspend fun loginUser(username: String, password: String): Boolean // Retourne true pour succès, false pour échec

    /**
     * Observe si un compte utilisateur existe dans la base de données.
     * Utile pour déterminer s'il faut afficher l'écran de connexion ou d'inscription au démarrage.
     *
     * @return Un Flow émettant `true` si un utilisateur est enregistré, `false` sinon.
     */
    fun doesUserExist(): Flow<Boolean>

    /**
     * Récupère le nom d'utilisateur de l'utilisateur enregistré, s'il existe.
     *
     * @return Un Flow émettant le nom d'utilisateur ou null si aucun utilisateur n'est enregistré.
     */
    fun getRegisteredUser(): Flow<UserUiModel?>

    /**
     * Supprime toutes les données utilisateur de la base de données.
     * Agit comme une fonction de "déconnexion" ou de réinitialisation dans ce scénario simplifié.
     */
    suspend fun clearUserData()

}
