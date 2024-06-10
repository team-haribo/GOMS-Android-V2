package com.goms.main.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.main.R

@Composable
internal fun FilterText(onFilterTextClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .gomsClickable { onFilterTextClick() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.filter),
            style = typography.buttonLarge,
            fontWeight = FontWeight.Normal,
            color = colors.A7
        )
    }
}
