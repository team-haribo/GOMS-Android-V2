package com.goms.design_system.component.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GomsSpacer(
    size: SpacerSize = SpacerSize.Medium,
    height: Dp = size.height.dp
) {
    Spacer(modifier = Modifier.height(height))
}