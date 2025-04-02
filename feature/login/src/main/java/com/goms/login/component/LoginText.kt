package com.goms.login.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.login.R

@Composable
internal fun LoginText() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = stringResource(id = R.string.wednesday_out_system),
                style = typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.P5
            )
            Text(
                text = stringResource(id = R.string.management_service),
                style = typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.WHITE
            )
        }
        GomsSpacer(size = SpacerSize.ExtraSmall)
        Text(
            text = stringResource(id = R.string.wednesday_out_easy_try),
            style = typography.textMedium,
            fontWeight = FontWeight.Normal,
            color = colors.G4,
            textAlign = TextAlign.Center
        )
    }
}