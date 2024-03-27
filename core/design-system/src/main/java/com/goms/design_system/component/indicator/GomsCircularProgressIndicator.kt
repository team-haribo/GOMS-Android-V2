package com.goms.design_system.component.indicator

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors

@Composable
fun GomsCircularProgressIndicator() {
    val colorsList = listOf(
        colors.P5,
        colors.P5.copy(alpha = 0f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f))
            .gomsClickable {},
        contentAlignment = Alignment.Center
    ) {
        val transition = rememberInfiniteTransition()
        val translateAnimation by transition.animateFloat(
            initialValue = 360f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        Canvas(modifier = Modifier.size(size = 60.dp)) {
            val startAngle = 5f
            val sweepAngle = 350f

            rotate(translateAnimation) {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = colorsList,
                        center = Offset(size.width / 2f, size.height / 2f)
                    ),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(6 / 2f, 6 / 2f),
                    style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round),
                )
            }
        }
    }
}
