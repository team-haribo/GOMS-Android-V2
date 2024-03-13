package com.goms.design_system.component.clickable

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

fun Modifier.gomsClickable(
    enabled: Boolean = true,
    isIndication: Boolean = false,
    rippleColor: Color? = null,
    onClickLabel: String? = null,
    role: Role? = null,
    interval: Long = 1000L,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get(intervalMs = interval) }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = if (isIndication) {
            rippleColor?.let {
                rememberRipple(
                    color = it
                )
            } ?: LocalIndication.current
        } else null,
        interactionSource = remember { MutableInteractionSource() }
    )
}