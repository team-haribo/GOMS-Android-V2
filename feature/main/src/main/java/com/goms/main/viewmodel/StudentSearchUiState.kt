package com.goms.main.viewmodel

import com.goms.model.response.council.StudentResponseModel

sealed interface StudentSearchUiState {
    object Loading : StudentSearchUiState
    object Empty : StudentSearchUiState
    object QueryEmpty : StudentSearchUiState
    data class Success(val studentSearchResponse: List<StudentResponseModel>) : StudentSearchUiState
    data class Error(val exception: Throwable) : StudentSearchUiState
}