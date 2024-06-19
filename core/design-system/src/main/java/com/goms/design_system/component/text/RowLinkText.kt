package com.goms.design_system.component.text

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.goms.design_system.R
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.theme.ThemeType

@Composable
fun RowLinkText(
    onLinkTextClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.forget_password),
            style = typography.buttonLarge,
            fontWeight = FontWeight.Normal,
            color = colors.G4
        )
        Text(
            modifier = Modifier.gomsClickable { onLinkTextClick() },
            text = stringResource(id = R.string.find_password),
            style = typography.buttonLarge,
            fontWeight = FontWeight.Normal,
            color = colors.P5
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun RowLinkTextPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        RowLinkText {}
    }
}
