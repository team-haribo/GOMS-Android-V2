package com.goms.main.viewmodel

import com.goms.model.response.late.RankResponseModel

sealed interface GetLateRankListUiState {
    object Loading : GetLateRankListUiState
    object Empty : GetLateRankListUiState
    data class Success(val getLateRankListResponse: List<RankResponseModel>) : GetLateRankListUiState
    data class Error(val exception: Throwable) : GetLateRankListUiState
}