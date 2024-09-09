package com.goms.design_system.component.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.ThemePreviews

fun Modifier.shimmerEffect(
    color: Color = Color.LightGray,
    targetValue: Float = 1_300f,
    shape: Shape = CircleShape
) = composed {
    val brush = ShimmerBrush(
        color = color,
        targetValue = targetValue
    )
    this.then(background(brush, shape))
}

@ThemePreviews
@Composable
private fun GomsCircularProgressIndicatorPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .shimmerEffect()
        )
    }
}