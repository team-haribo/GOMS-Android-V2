package com.goms.main.viewmodel

import com.goms.model.response.outing.OutingResponseModel

sealed interface OutingSearchUiState {
    object Loading : OutingSearchUiState
    object Empty : OutingSearchUiState
    object QueryEmpty : OutingSearchUiState
    data class Success(val outingSearchResponse: List<OutingResponseModel>) : OutingSearchUiState
    data class Error(val exception: Throwable) : OutingSearchUiState
}