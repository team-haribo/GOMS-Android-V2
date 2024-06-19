package com.goms.design_system.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.icon.CloseIcon
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.util.gomsPreview

@Composable
fun BottomSheetHeader(
    modifier: Modifier = Modifier,
    title: String,
    closeSheet: () -> Unit
) {
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
    GomsSpacer(size = SpacerSize.Small)
}

@Preview
@Composable
fun BottomSheetHeaderPreview() {
    gomsPreview {
        BottomSheetHeader(title = "바텀 시트") {}
    }
}
