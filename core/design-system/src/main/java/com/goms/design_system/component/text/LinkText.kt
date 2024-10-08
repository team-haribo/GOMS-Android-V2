package com.goms.design_system.component.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.R
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.ThemePreviews

@Composable
fun LinkText(
    text: String,
    onLinkTextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = colors.WHITE.copy(0.15f)
            )
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = stringResource(id = R.string.first_time),
                style = typography.caption,
                fontWeight = FontWeight.Normal,
                color = colors.G4
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = colors.WHITE.copy(0.15f)
            )
        }
        Box(
            modifier = Modifier
                .height(48.dp)
                .gomsClickable { onLinkTextClick() }
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                style = typography.buttonLarge,
                fontWeight = FontWeight.Normal,
                color = colors.P5
            )
        }
    }
}

@ThemePreviews
@Composable
private fun LinkTextPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        LinkText(text = "GOMS") {}
    }
}
