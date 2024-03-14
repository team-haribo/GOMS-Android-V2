package com.goms.re_password.viewmodel

sealed interface VerifyNumberUiState {
    object Loading : VerifyNumberUiState
    object Success : VerifyNumberUiState
    object BadRequest : VerifyNumberUiState
    object NotFound : VerifyNumberUiState
    data class Error(val exception: Throwable): VerifyNumberUiState
}