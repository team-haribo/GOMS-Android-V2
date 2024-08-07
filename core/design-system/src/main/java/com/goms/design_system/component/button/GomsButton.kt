package com.goms.design_system.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.R
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.icon.GAuthIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.ThemePreviews

@Composable
fun GomsButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    state: ButtonState = ButtonState.Normal,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(48.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (state) {
                ButtonState.Enable -> Color(0xFFB2B2B2)
                ButtonState.Normal -> colors.P5
                ButtonState.Logout -> colors.N5
            },
            contentColor = when (state) {
                ButtonState.Enable -> Color(0xFF666666)
                else -> Color.White
            }
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


@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
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
            text = stringResource(id = R.string.gauth_login),
            style = typography.buttonLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
fun BottomSheetButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) colors.P5.copy(0.25f) else colors.G1)
            .border(
                width = 1.dp,
                color = if (selected) Color.Transparent else colors.WHITE.copy(0.15f),
                shape = RoundedCornerShape(12.dp)
            )
            .gomsClickable { onClick() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = typography.buttonMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) colors.P5 else colors.G7
        )
    }
}


@Composable
fun AdminBottomSheetButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) colors.A7.copy(0.25f) else colors.G1)
            .border(
                width = 1.dp,
                color = if (selected) Color.Transparent else colors.WHITE.copy(0.15f),
                shape = RoundedCornerShape(12.dp)
            )
            .gomsClickable { onClick() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = typography.buttonMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) colors.A7 else colors.G7
        )
    }
}


@Composable
fun InitBottomSheetButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colors.N5.copy(0.25f))
            .border(
                width = 1.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .gomsClickable { onClick() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = typography.buttonMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.N5
        )
    }
}

@ThemePreviews
@Composable
private fun GomsButtonPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
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
            BottomSheetButton(
                modifier = Modifier.fillMaxWidth(),
                text = "버튼",
                selected = true,
            ) {}
            BottomSheetButton(
                modifier = Modifier.fillMaxWidth(),
                text = "버튼",
                selected = false,
            ) {}
            AdminBottomSheetButton(
                modifier = Modifier.fillMaxWidth(),
                text = "버튼",
                selected = true,
            ) {}
            InitBottomSheetButton(
                modifier = Modifier.fillMaxWidth(),
                text = "버튼"
            ) {}
        }
    }
}