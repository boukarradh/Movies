package com.epsi.movies.presentation.features.register.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epsi.movies.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour la gestion de l'écran d'inscription (version très simplifiée).
 * Ce ViewModel interagit avec le [UserRepository] pour enregistrer un nouvel utilisateur.
 *
 * Il est annoté avec [@HiltViewModel] pour permettre l'injection de dépendances (comme le UserRepository)
 * via Hilt.
 *
 * **Note:** Cette version est très basique. Elle ne gère pas l'état de l'UI (chargement, succès, erreur)
 * et ne fournit pas de retour à l'UI sur le résultat de l'opération.
 * Une version complète utiliserait un StateFlow pour exposer l'état.
 *
 * @property userRepository Le repository injecté qui fournit l'accès aux opérations
 * liées aux données utilisateur (ici, l'enregistrement).
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() { // Hérite de ViewModel pour bénéficier du cycle de vie et de viewModelScope

    /**
     * Tente d'enregistrer un nouvel utilisateur avec le nom d'utilisateur et le mot de passe fournis.
     * Lance une opération asynchrone pour appeler le repository.
     *
     * **Important:** Dans cette version simplifiée, cette fonction ne retourne aucun résultat
     * et ne gère pas les exceptions (par exemple, si le nom d'utilisateur est déjà pris).
     * L'UI ne sera pas informée du succès ou de l'échec de l'enregistrement.
     *
     * @param userName Le nom d'utilisateur saisi par l'utilisateur.
     * @param password Le mot de passe saisi par l'utilisateur.
     */
    fun saveUser(userName: String, password: String) {
        // Lance une nouvelle coroutine dans le scope lié au cycle de vie de ce ViewModel.
        // Les opérations lancées ici seront automatiquement annulées si le ViewModel est détruit.
        // Il est généralement préférable que ce soit
        // le Repository qui choisisse le dispatcher approprié pour ses opérations internes.
        viewModelScope.launch {
            try {
                // Appelle la fonction suspendue registerUser du repository.
                // Cette fonction (dans notre implémentation) retourne l'ID ou lève une exception.
                userRepository.registerUser(userName, password)
                // Aucune gestion du succès ici dans cette version simple.
                // On pourrait logger un succès si besoin : Log.d("RegisterViewModel", "Enregistrement réussi pour $userName")
            } catch (e: Exception) {
                // Aucune gestion de l'erreur ici dans cette version simple.
                // L'erreur est juste interceptée pour éviter un crash potentiel non géré.
                // On pourrait logger l'erreur : Log.e("RegisterViewModel", "Erreur enregistrement pour $userName", e)
            }
        }
    }
}