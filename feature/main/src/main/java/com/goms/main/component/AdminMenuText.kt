package com.goms.main.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme
import com.goms.main.R

@Composable
internal fun AdminMenuText(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.admin_menu),
        style = GomsTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = GomsTheme.colors.WHITE
    )
}