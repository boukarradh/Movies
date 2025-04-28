package com.epsi.movies.presentation.ui.designsystem

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun PasswordOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit = {},

    ) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }),

        )
}