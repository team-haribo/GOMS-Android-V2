package com.goms.setting.viewmodel

sealed interface ProfileImageUiState {
    object Loading : ProfileImageUiState
    object Success : ProfileImageUiState
    object EmptyProfileUrl : ProfileImageUiState
    data class Error(val exception: Throwable) : ProfileImageUiState
}