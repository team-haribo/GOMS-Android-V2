package com.goms.design_system.component.button

import androidx.compose.runtime.Composable
import com.msg.gauthsignin.component.GAuthButton
import com.msg.gauthsignin.component.utils.Types

@Composable
fun AuthButton(onClick: () -> Unit) {
    GAuthButton(
        style = Types.Style.DEFAULT,
        actionType = Types.ActionType.SIGNIN,
        colors = Types.Colors.COLORED,
        horizontalPercent = 1f
    ) {
        onClick()
    }
}