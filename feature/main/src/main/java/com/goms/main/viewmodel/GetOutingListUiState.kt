package com.goms.main.viewmodel

import com.goms.model.response.outing.OutingResponse

sealed interface GetOutingListUiState {
    object Loading : GetOutingListUiState
    data class Success(val getOutingListResponse: List<OutingResponse>) : GetOutingListUiState
    data class Error(val exception: Throwable) : GetOutingListUiState
}