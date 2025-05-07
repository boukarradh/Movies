package com.epsi.movies.presentation.features.login

import androidx.compose.foundation.Image
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.epsi.movies.R
import com.epsi.movies.presentation.features.login.viewModel.LoginViewModel
import com.epsi.movies.presentation.ui.designsystem.CustomElevatedButton
import com.epsi.movies.presentation.ui.designsystem.MoviesAlertDialog
import com.epsi.movies.presentation.ui.designsystem.PasswordOutlinedTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var displayPopup: Boolean by remember { mutableStateOf(false) }


    when (displayPopup) {
        true -> {
            MoviesAlertDialog(
                title = "Erreur de connexion",
                onConfirm = {
                    username = ""
                    password = ""
                },
                onDismiss = {
                    username = ""
                    password = ""
                })
        }

        false -> {}
    }


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
                loginViewModel.loginUser(
                    userName = username,
                    password = password,
                    onLoginResult = { userExist ->
                        if (userExist) {
                            onLoginSuccess()
                        } else {
                            displayPopup = true
                        }
                    }
                )
            },
            enabled = (username.isNotBlank() && password.isNotBlank()),
            text = "Login"
        )

        TextButton(onClick = { onNavigateToRegister() }) {
            Text(
                text = "Créer un compte",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }



        Spacer(modifier = Modifier.height(24.dp))

    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}