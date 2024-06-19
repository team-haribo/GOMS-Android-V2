package com.goms.design_system.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.R
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.icon.BackIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

@Composable
fun GomsBackButton(
    modifier: Modifier = Modifier.height(48.dp),
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(start = 15.dp)
            .gomsClickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        BackIcon(tint = colors.P5)
        Text(
            text = stringResource(id = R.string.go_back),
            style = typography.textMedium,
            fontWeight = FontWeight.Normal,
            color = colors.P5
        )
    }
}

@Preview
@Composable
fun GomsBackButtonPreview() {
    GomsTheme {
        GomsBackButton {}
    }
}
