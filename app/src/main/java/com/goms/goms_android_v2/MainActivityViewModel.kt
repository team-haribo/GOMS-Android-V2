package com.goms.goms_android_v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.data.repository.account.AccountRepository
import com.goms.model.response.account.ProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = flow {
        accountRepository.getProfile().collect { profileResponse ->
            emit(profileResponse)
        }
    }
        .asResult()
        .map { result ->
            when (result) {
                Result.Loading -> MainActivityUiState.Loading
                is Result.Success -> MainActivityUiState.Success(result.data)
                is Result.Error -> MainActivityUiState.Error(result.exception)
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = MainActivityUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )
}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val getProfileResponse: ProfileResponse) : MainActivityUiState
    data class Error(val exception: Throwable) : MainActivityUiState
}