package com.goms.main.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.util.formatDate
import kotlinx.datetime.LocalDate

@Composable
internal fun LocalDateText(date: LocalDate) {
    Text(
        text = date.formatDate(),
        style = typography.textSmall,
        fontWeight = FontWeight.SemiBold,
        color = colors.WHITE
    )
}