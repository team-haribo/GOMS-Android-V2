package com.goms.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.domain.auth.LoginUseCase
import com.goms.domain.auth.SaveTokenUseCase
import com.goms.model.request.auth.LoginRequest
import com.goms.model.response.auth.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {
    private val _loginResponse = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
    val loginResponse = _loginResponse.asStateFlow()

    private val _saveTokenResponse = MutableStateFlow<Result<Unit>>(Result.Loading)
    val saveTokenResponse = _saveTokenResponse.asStateFlow()

    fun login(body: LoginRequest) = viewModelScope.launch {
        loginUseCase(body = body)
            .asResult()
            .collectLatest{ result ->
                when (result) {
                    is Result.Loading -> _loginResponse.value = LoginUiState.Loading
                    is Result.Success -> _loginResponse.value = LoginUiState.Success(result.data)
                    is Result.Error -> _loginResponse.value = LoginUiState.Error(result.exception)
                }
            }
    }

    fun saveToken(token: LoginResponse) = viewModelScope.launch {
        saveTokenUseCase(token = token)
            .onSuccess {
                _saveTokenResponse.value = Result.Success(it)
            }.onFailure {
                _saveTokenResponse.value = Result.Error(it)
            }
    }
}