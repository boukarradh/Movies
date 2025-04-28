package com.epsi.movies.presentation.features.login

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen( modifier: Modifier = Modifier,onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
        Text(
        text = "Welcome to Login Screen!",
        modifier = modifier
    )
}