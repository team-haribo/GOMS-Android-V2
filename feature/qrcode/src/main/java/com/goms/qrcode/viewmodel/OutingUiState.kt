package com.goms.qrcode.viewmodel

sealed interface OutingUiState {
    object Loading : OutingUiState
    object Success : OutingUiState
    object BadRequest : OutingUiState
    data class Error(val exception: Throwable) : OutingUiState
}