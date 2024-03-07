package com.goms.setting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.datastore.AuthTokenDataSource
import com.goms.domain.account.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SettingViewModel @Inject constructor (
    private val authTokenDataSource: AuthTokenDataSource,
    private val getProfileUseCase: GetProfileUseCase,
) : ViewModel() {
    val role = authTokenDataSource.getAuthority()

    private val _getProfileUiState = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Loading)
    val getProfileUiState = _getProfileUiState.asStateFlow()
    fun getProfile() = viewModelScope.launch {
        getProfileUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getProfileUiState.value = GetProfileUiState.Loading
                    is Result.Success -> {
                        Log.d("testt","suc")
                        _getProfileUiState.value = GetProfileUiState.Success(result.data)
                    }
                    is Result.Error -> _getProfileUiState.value = GetProfileUiState.Error(result.exception)
                }
            }
    }

    fun initGetProfile() {
        _getProfileUiState.value = GetProfileUiState.Loading
    }
}