package com.goms.find_password.viewmodel.uistate

interface FindPasswordUiState {
    object Loading : FindPasswordUiState
    object Success : FindPasswordUiState
    object BadRequest : FindPasswordUiState
    data class Error(val exception: Throwable): FindPasswordUiState
}