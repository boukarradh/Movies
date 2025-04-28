package com.epsi.movies.data.converter

import com.epsi.movies.data.db.entities.UserEntity
import com.epsi.movies.domain.model.user.UserUiModel

/**
 * Convertit un objet UserUiModel (de l'UI) en UserEntity (pour la base de données).
 * Attention: Le mot de passe n'étant pas dans le UserUiModel, il doit être fourni séparément.
 * Cette fonction est moins courante, car on crée souvent une UserEntity directement
 * à partir des données saisies dans l'UI (username + password).
 *
 * @receiver L'objet UserUiModel source.
 * @param password Le mot de passe (en clair pour ce TP) à associer à cette entité.
 * @return L'objet UserEntity correspondant.
 */
fun UserUiModel.toEntity(password: String): UserEntity {

    return UserEntity(
        userId = this.userId, // Utilise l'ID existant du modèle UI
        username = this.username,
        password = password // Le mot de passe est fourni en paramètre
    )
}