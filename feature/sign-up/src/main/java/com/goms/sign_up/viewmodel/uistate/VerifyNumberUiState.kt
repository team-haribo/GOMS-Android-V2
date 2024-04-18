package com.goms.sign_up.viewmodel.uistate

sealed interface VerifyNumberUiState {
    object Loading : VerifyNumberUiState
    object Success : VerifyNumberUiState
    object BadRequest : VerifyNumberUiState
    object NotFound : VerifyNumberUiState
    data class Error(val exception: Throwable) : VerifyNumberUiState
}