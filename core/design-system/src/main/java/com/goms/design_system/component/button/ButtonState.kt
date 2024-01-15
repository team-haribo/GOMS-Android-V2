package com.goms.design_system.component.button

sealed class ButtonState {
    object Enable: ButtonState()
    object Normal: ButtonState()
    object Logout: ButtonState()
}