package com.epsi.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epsi.movies.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    userRepository: UserRepository // Hilt injecte le repository
) : ViewModel() {
    // Observe si un utilisateur existe via le repository
    // Utilise stateIn pour convertir le Flow (cold flow) en StateFlow (hot flow)

    val userExists: StateFlow<Boolean?> = userRepository.doesUserExist() // Supposant que doesUserExist retourne Flow<Boolean>
        .stateIn(
            scope = viewModelScope, // Scope lié au ViewModel
            started = SharingStarted.WhileSubscribed(5000), // Commence l'observation quand l'UI est visible
            initialValue = null // Commence par null en attendant la première valeur de la DB
        )
}