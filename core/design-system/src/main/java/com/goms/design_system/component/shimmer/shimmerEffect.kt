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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.util.gomsPreview

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

@Preview
@Composable
fun GomsCircularProgressIndicatorPreview() {
    gomsPreview {
        Box(
            modifier = Modifier
                .size(50.dp)
                .shimmerEffect()
        )
    }
}