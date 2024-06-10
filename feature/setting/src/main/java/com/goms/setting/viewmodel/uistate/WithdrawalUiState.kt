package com.goms.setting.viewmodel.uistate

interface WithdrawalUiState {
    object Loading : WithdrawalUiState
    object Success : WithdrawalUiState
    object BadRequest : WithdrawalUiState
    data class Error(val exception: Throwable) : WithdrawalUiState
}