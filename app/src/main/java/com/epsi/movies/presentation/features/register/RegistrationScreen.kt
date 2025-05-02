package com.epsi.movies.presentation.features.register


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.epsi.movies.R
import com.epsi.movies.presentation.features.register.viewModel.RegisterViewModel
import com.epsi.movies.presentation.ui.designsystem.CustomElevatedButton
import com.epsi.movies.presentation.ui.designsystem.PasswordOutlinedTextField
import com.epsi.movies.presentation.ui.theme.MoviesTheme

@Composable
fun RegistrationScreen(
    onRegisterSuccess: () -> Unit = {},
    registerViewModel: RegisterViewModel = hiltViewModel()
) {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }


        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(64.dp))

            Image(
                modifier = Modifier.size(80.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.img)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bienvenue !",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Créez votre compte pour commencer à explorer les films.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Champ pour le nom d'utilisateur
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nom d'utilisateur") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )


            // Champ pour le mot de passe

            PasswordOutlinedTextField(
                text = password,
                onValueChange = { userInput -> password = userInput },
                label = "Mot de passe",
                modifier = Modifier.fillMaxWidth(),
                imeAction = ImeAction.Next

            )

            // Champ pour la confirmation du mot de passe
            PasswordOutlinedTextField(
                text = confirmPassword,
                onValueChange = { userInput -> confirmPassword = userInput },
                label = "Confirmer le mot de passe",
                modifier = Modifier.fillMaxWidth(),
                onDone = {
                    focusManager.clearFocus() // Ferme le clavier
                }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
            CustomElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {
                    registerViewModel.saveUser(userName = username, password = password)
                },
                enabled = (username.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) && (password == confirmPassword),
                text = "S'inscrire"
            )


            Spacer(modifier = Modifier.height(24.dp))

    }
}

@Preview(showBackground = true, name = "Register Screen Preview")
@Composable
fun RegisterScreenPreview() {
    MoviesTheme { // Assurez-vous que MoviesTheme est défini
        RegistrationScreen()
    }
}