package com.goms.setting.viewmodel.uistate

sealed interface SetThemeUiState {
    object Loading : SetThemeUiState
    object Success : SetThemeUiState
    data class Error(val exception: Throwable) : SetThemeUiState
}