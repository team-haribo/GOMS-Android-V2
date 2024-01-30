package com.goms.main.viewmodel

import com.goms.model.response.outing.CountResponse

sealed interface GetOutingCountUiState {
    object Loading : GetOutingCountUiState
    object Empty : GetOutingCountUiState
    data class Success(val getOutingCountResponse: CountResponse) : GetOutingCountUiState
    data class Error(val exception: Throwable) : GetOutingCountUiState
}