package com.goms.design_system.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.icon.CloseIcon
import com.goms.design_system.theme.GomsTheme

@Composable
fun BottomSheetHeader(
    modifier: Modifier,
    title: String,
    closeSheet: () -> Unit
) {
    GomsTheme { colors, typography ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = colors.WHITE
            )
            CloseIcon(
                modifier = Modifier.gomsClickable { closeSheet() },
                tint = colors.WHITE
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}