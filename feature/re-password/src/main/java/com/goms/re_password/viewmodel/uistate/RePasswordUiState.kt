package com.goms.re_password.viewmodel.uistate

interface RePasswordUiState {
    object Loading : RePasswordUiState
    object Success : RePasswordUiState
    object BadRequest : RePasswordUiState
    data class Error(val exception: Throwable): RePasswordUiState
}