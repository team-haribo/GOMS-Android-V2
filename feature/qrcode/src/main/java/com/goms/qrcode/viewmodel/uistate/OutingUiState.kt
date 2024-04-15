package com.goms.qrcode.viewmodel.uistate

sealed interface OutingUiState {
    object Loading : OutingUiState
    object Success : OutingUiState
    object BadRequest : OutingUiState
    data class Error(val exception: Throwable) : OutingUiState
}