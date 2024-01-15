package com.goms.design_system.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.icon.GAuthIcon
import com.goms.design_system.theme.GomsTheme

@Composable
fun GomsButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    state: ButtonState = ButtonState.Normal,
    onClick: () -> Unit,
) {
    GomsTheme { colors, typography ->
        val backgroundColor: (buttonState: ButtonState) -> Color = {
            when (it) {
                ButtonState.Enable -> Color(0xFFB2B2B2)
                ButtonState.Normal -> colors.P5
                ButtonState.Logout -> colors.N5
            }
        }

        val contentColor: (buttonState: ButtonState) -> Color = {
            when (it) {
                ButtonState.Enable -> Color(0xFF666666)
                ButtonState.Normal -> Color.White
                ButtonState.Logout -> Color.White
            }
        }

        Button(
            modifier = modifier.height(48.dp),
            enabled = enabled,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor(state),
                contentColor = contentColor(state),
            ),
            onClick = { if (state != ButtonState.Enable) onClick() }
        ) {
            Text(
                text = text,
                style = typography.buttonLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    GomsTheme { colors, typography ->
        Button(
            modifier = modifier.height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.I6,
                contentColor = Color.White,
            ),
            onClick = { onClick() }
        ) {
            GAuthIcon()
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Sign in with GAuth",
                style = typography.buttonLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GomsButtonPreview() {
    Column {
        GomsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "버튼",
            state = ButtonState.Enable,
            onClick = {}
        )
        GomsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "버튼",
            state = ButtonState.Normal,
            onClick = {}
        )
        GomsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "버튼",
            state = ButtonState.Logout,
            onClick = {}
        )
        AuthButton(modifier = Modifier.fillMaxWidth()) {}
    }
}