package com.goms.setting.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme
import com.goms.setting.R

@Composable
internal fun WithdrawalText(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.withdrawal),
        style = GomsTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = GomsTheme.colors.WHITE
    )
}