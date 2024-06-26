package com.goms.find_password.viewmodel.uistate

sealed interface VerifyNumberUiState {
    object Loading : VerifyNumberUiState
    object Success : VerifyNumberUiState
    object BadRequest : VerifyNumberUiState
    object NotFound : VerifyNumberUiState
    object TooManyRequest : VerifyNumberUiState
    data class Error(val exception: Throwable) : VerifyNumberUiState
}