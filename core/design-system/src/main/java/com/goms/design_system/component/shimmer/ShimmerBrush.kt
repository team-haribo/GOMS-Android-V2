package com.goms.design_system.component.shimmer

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ShimmerBrush(
    color: Color = Color.LightGray,
    targetValue: Float = 1300f
): Brush {
    val shimmerColors = listOf(
        color.copy(alpha = 0.15f),
        color.copy(alpha = 0.13f),
        color.copy(alpha = 0.1f),
        color.copy(alpha = 0.13f),
        color.copy(alpha = 0.15f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ), repeatMode = RepeatMode.Restart
        )
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )
}