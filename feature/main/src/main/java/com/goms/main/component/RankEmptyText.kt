package com.goms.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.icon.FireIcon
import com.goms.design_system.theme.GomsTheme

@Composable
internal fun RankEmptyText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(GomsTheme.colors.G1)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FireIcon(tint = GomsTheme.colors.WHITE)
        Text(
            text = "지각자가 없네요! 축하해요!",
            style = GomsTheme.typography.textSmall,
            fontWeight = FontWeight.SemiBold,
            color = GomsTheme.colors.WHITE
        )
    }
}