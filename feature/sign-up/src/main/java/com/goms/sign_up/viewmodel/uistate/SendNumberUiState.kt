package com.goms.sign_up.viewmodel.uistate

sealed interface SendNumberUiState {
    object Loading : SendNumberUiState
    object Success : SendNumberUiState
    object EmailNotValid : SendNumberUiState
    data class Error(val exception: Throwable) : SendNumberUiState
}