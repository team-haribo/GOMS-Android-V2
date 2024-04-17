package com.goms.sign_up.viewmodel.uistate

sealed interface SignUpUiState {
    object Loading : SignUpUiState
    object Success : SignUpUiState
    object Conflict : SignUpUiState
    object PasswordMismatch : SignUpUiState
    object PasswordNotValid : SignUpUiState
    data class Error(val exception: Throwable) : SignUpUiState
}