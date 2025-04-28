package com.epsi.movies.domain.model.user

/**
 * Représente les informations d'un utilisateur à afficher ou utiliser
 * dans l'interface utilisateur (UI).
 * Ce modèle est dérivé de UserEntity mais n'expose pas le mot de passe.
 */
data class UserUiModel(
    val userId: Long, // L'identifiant unique de l'utilisateur
    val username: String, // Le nom d'utilisateur à afficher
)