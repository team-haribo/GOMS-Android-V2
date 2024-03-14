package com.goms.setting.viewmodel

import com.goms.model.response.account.ProfileResponse

sealed interface LogoutUiState {
    object Loading : LogoutUiState
    object Success : LogoutUiState
    data class Error(val exception: Throwable) : LogoutUiState
}