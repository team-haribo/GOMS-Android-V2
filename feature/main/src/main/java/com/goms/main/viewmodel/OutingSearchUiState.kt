package com.goms.main.viewmodel

import com.goms.model.response.outing.OutingResponse

sealed interface OutingSearchUiState {
    object Loading : OutingSearchUiState
    object Empty : OutingSearchUiState
    object QueryEmpty : OutingSearchUiState
    data class Success(val outingSearchResponse: List<OutingResponse>) : OutingSearchUiState
    data class Error(val exception: Throwable) : OutingSearchUiState
}