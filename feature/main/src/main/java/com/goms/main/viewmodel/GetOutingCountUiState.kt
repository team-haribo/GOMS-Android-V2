package com.goms.main.viewmodel

import com.goms.model.response.outing.CountResponseModel

sealed interface GetOutingCountUiState {
    object Loading : GetOutingCountUiState
    object Empty : GetOutingCountUiState
    data class Success(val getOutingCountResponseModel: CountResponseModel) : GetOutingCountUiState
    data class Error(val exception: Throwable) : GetOutingCountUiState
}