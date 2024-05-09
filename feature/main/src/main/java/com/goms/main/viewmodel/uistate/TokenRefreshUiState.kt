package com.goms.main.viewmodel.uistate

import com.goms.model.response.auth.LoginResponseModel

sealed interface TokenRefreshUiState {
    object Loading : TokenRefreshUiState
    data class Success(val token: LoginResponseModel) : TokenRefreshUiState
    data class Error(val exception: Throwable) : TokenRefreshUiState
}