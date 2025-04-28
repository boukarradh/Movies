package com.epsi.movies.data.converter

import com.epsi.movies.data.db.entities.UserEntity
import com.epsi.movies.domain.model.user.UserUiModel

/**
 * Convertit un objet UserEntity (de la base de données) en UserUiModel (pour l'UI).
 *
 * @receiver L'objet UserEntity source.
 * @param isLoggedIn Indique si cet utilisateur doit être marqué comme connecté dans le modèle UI.
 * Cette information vient généralement de l'état de session géré ailleurs
 * (ex: DataStore ou un ViewModel d'authentification).
 * @return L'objet UserUiModel correspondant.
 */
fun UserEntity.toUiModel(): UserUiModel {
    return UserUiModel(
        userId = this.userId,
        username = this.username,
    )
}