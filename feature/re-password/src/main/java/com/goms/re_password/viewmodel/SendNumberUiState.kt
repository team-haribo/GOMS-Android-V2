package com.goms.re_password.viewmodel

sealed interface SendNumberUiState {
    object Loading : SendNumberUiState
    object Success : SendNumberUiState
    data class Error(val exception: Throwable): SendNumberUiState
}