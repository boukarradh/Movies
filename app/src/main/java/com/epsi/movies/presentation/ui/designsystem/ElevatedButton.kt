package com.epsi.movies.presentation.ui.designsystem
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.epsi.movies.presentation.ui.theme.ColorAccent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomElevatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColor: Color = ColorAccent,
    contentColor: Color = White,
    text: String,
    style: TextStyle = TextStyle(fontWeight = FontWeight.Normal)
) {
    ElevatedButton(
        onClick = onClick, modifier = modifier
            .height(50.dp)
            .clearAndSetSemantics {
                role = Role.Button
                contentDescription = text
            }, enabled = enabled, colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor, contentColor = contentColor
        ), shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            modifier = Modifier.semantics { invisibleToUser() },
            text = text,
            style = style
        )
    }
}
