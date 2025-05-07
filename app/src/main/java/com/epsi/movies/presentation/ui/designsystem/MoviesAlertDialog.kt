package com.epsi.movies.presentation.ui.designsystem

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@Composable
fun MoviesAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonName: String ="Ok",
    dismissButtonName: String? = null,
    title: String? = null,
    text: String? = null,
) {
    AlertDialog(modifier = modifier,
        onDismissRequest = {
            onDismiss()
        },
        title = { title?.let { Text(text = it) } },
        text = { text?.let { Text(text = it) } },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(text = confirmButtonName.uppercase())
            }
        },
        dismissButton = dismissButtonName?.let {
            {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = it.uppercase())
                }
            }
        }
    )
}