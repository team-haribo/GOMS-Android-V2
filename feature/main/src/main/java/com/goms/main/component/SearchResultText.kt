package com.goms.main.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.main.R

@Composable
internal fun SearchResultText(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.search_result),
        style = typography.textLarge,
        fontWeight = FontWeight.SemiBold,
        color = colors.WHITE
    )
}
