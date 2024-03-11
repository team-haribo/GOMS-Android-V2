package com.goms.design_system.component.button

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.goms.design_system.theme.GomsTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun GomsSwitchButton(
    height: Dp = 32.dp,
    width: Dp = 52.dp,
    circleSize: Dp = 26.dp,
    circleHorizontalButtonPadding: Dp = 3.dp,
    circleVerticalButtonPadding: Dp = 2.dp,
    switchOnBackground: Color = Color.Unspecified,
    switchOffBackground: Color = Color.Unspecified,
    stateOn: Int,
    stateOff: Int,
    initialValue: Int,
    onCheckedChanged: (checked: Boolean) -> Unit
) {
    var clickListener by remember { mutableStateOf(false) }

    val swipeableState =
        rememberSwipeableState(initialValue = initialValue, confirmStateChange = { true })

    val sizePx = with(LocalDensity.current) { (width - height).toPx() }
    val anchors = mapOf(0f to stateOff, sizePx to stateOn) // Maps anchor points (in px) to states
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            if (clickListener) onCheckedChanged(true) else onCheckedChanged(false)
        }
    }
    GomsTheme { colors, typography ->
        Box(
            modifier = Modifier.clickable {
                clickListener = !clickListener
                scope.launch {
                    if (clickListener) {
                        swipeableState.animateTo(stateOn)
                    } else {
                        swipeableState.animateTo(stateOff)
                    }
                }
            }
        ) {
            Row(
                modifier = Modifier
                    .height(height)
                    .width(width)
                    .clip(RoundedCornerShape(height))
                    .background(
                        if (swipeableState.currentValue == stateOff) {
                            if (clickListener) switchOnBackground else switchOffBackground
                        } else {
                            if (clickListener) switchOnBackground else switchOffBackground
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    Modifier
                        .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                        .swipeable(
                            state = swipeableState,
                            anchors = anchors,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Orientation.Horizontal
                        )
                        .padding(
                            horizontal = circleHorizontalButtonPadding,
                            vertical = circleVerticalButtonPadding,
                        )
                        .clip(RoundedCornerShape(50))
                        .background(color = colors.BLACK)
                        .size(circleSize)
                )
            }
        }
    }
}