package com.goms.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.login.R

@Composable
internal fun LoginText() {
    Column {
        Row {
            Text(
                text = stringResource(id = R.string.wednesday_out_system),
                style = typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.P5
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.management_service),
                style = typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.WHITE
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.wednesday_out_easy_try),
            style = typography.textMedium,
            fontWeight = FontWeight.Normal,
            color = colors.G4,
            textAlign = TextAlign.Center
        )
    }
}
