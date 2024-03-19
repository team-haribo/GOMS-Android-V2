package com.goms.login.viewmodel

import com.goms.model.enum.Authority
import com.goms.model.response.auth.LoginResponse

sealed interface SaveTokenUiState {
    object Loading : SaveTokenUiState
    object Success : SaveTokenUiState
    data class Error(val exception: Throwable): SaveTokenUiState
}