package com.goms.sign_up.viewmodel

sealed interface VerifyNumberUiState {
    object Loading : VerifyNumberUiState
    object Success : VerifyNumberUiState
    data class Error(val exception: Throwable): VerifyNumberUiState
    object NotFound : VerifyNumberUiState
}