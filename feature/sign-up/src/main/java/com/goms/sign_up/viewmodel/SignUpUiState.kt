package com.goms.sign_up.viewmodel

sealed interface SignUpUiState {
    object Loading : SignUpUiState
    object Success : SignUpUiState
    object Conflict : SignUpUiState
    data class Error(val exception: Throwable): SignUpUiState
}