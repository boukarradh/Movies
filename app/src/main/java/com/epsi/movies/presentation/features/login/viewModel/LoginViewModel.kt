package com.epsi.movies.presentation.features.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epsi.movies.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour la gestion de l'écran de connexion.
 * Ce ViewModel interagit avec le [UserRepository] pour vérifier les identifiants
 * de l'utilisateur et communiquer le résultat à l'UI.
 *
 * Il est annoté avec [@HiltViewModel] pour permettre l'injection de dépendances (comme le UserRepository)
 * via Hilt. Si Hilt n'est pas utilisé, une Factory manuelle serait nécessaire.
 *
 * @property userRepository Le repository injecté qui fournit l'accès aux opérations
 * liées aux données utilisateur (connexion, inscription, etc.).
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() { // Hérite de ViewModel pour bénéficier du cycle de vie et de viewModelScope

    /**
     * Tente de connecter un utilisateur en vérifiant les identifiants fournis auprès du [UserRepository].
     * Cette opération est effectuée de manière asynchrone dans une coroutine.
     *
     * @param userName Le nom d'utilisateur saisi par l'utilisateur dans l'UI.
     * @param password Le mot de passe saisi par l'utilisateur dans l'UI.
     * @param onLoginResult Un callback (fonction lambda) qui sera appelé avec le résultat
     * de la tentative de connexion. Il prend un [Boolean] en paramètre :
     * `true` si la connexion a réussi, `false` sinon. Ce callback permet à l'UI
     * de réagir au résultat (par exemple, naviguer vers l'écran principal ou afficher une erreur).
     */
    fun loginUser(userName: String, password: String, onLoginResult: (Boolean) -> Unit) {
        // Lance une nouvelle coroutine dans le scope lié au cycle de vie de ce ViewModel.
        // Les opérations lancées ici seront automatiquement annulées si le ViewModel est détruit.
        // C'est le moyen recommandé pour lancer des opérations asynchrones depuis un ViewModel.
        viewModelScope.launch {
            // Appelle la fonction suspendue loginUser du repository.
            // Cette fonction (dans notre implémentation simplifiée) retourne directement true ou false.
            // Elle s'exécute sur un thread d'arrière-plan grâce à withContext(Dispatchers.IO) dans le repository.
            val loginSuccess: Boolean = userRepository.loginUser(userName, password)

            // Appelle la fonction de callback fournie par l'UI en lui passant le résultat (true ou false).
            // L'UI peut alors utiliser cette information pour mettre à jour son état ou déclencher une navigation.
            onLoginResult(loginSuccess)
        }
    }
}