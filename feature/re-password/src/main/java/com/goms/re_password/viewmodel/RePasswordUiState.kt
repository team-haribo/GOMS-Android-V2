package com.goms.re_password.viewmodel

interface RePasswordUiState {
    object Loading : RePasswordUiState
    object Success : RePasswordUiState
    object Conflict : RePasswordUiState
    data class Error(val exception: Throwable): RePasswordUiState
}