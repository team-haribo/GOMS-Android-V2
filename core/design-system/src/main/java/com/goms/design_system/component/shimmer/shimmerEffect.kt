package com.goms.design_system.component.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

fun Modifier.shimmerEffect(
    color: Color = Color.LightGray,
    targetValue: Float = 1300f,
    shape: Shape = CircleShape
) = composed {
    val brush = ShimmerBrush(
        color = color,
        targetValue = targetValue
    )
    this.then(background(brush, shape))
}