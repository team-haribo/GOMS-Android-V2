package com.goms.login.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.network.errorHandling
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.domain.auth.LoginUseCase
import com.goms.domain.auth.SaveTokenUseCase
import com.goms.model.request.auth.LoginRequestModel
import com.goms.model.response.auth.LoginResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
    val loginUiState = _loginUiState.asStateFlow()

    private val _saveTokenUiState = MutableStateFlow<SaveTokenUiState>(SaveTokenUiState.Loading)
    val saveTokenUiState = _saveTokenUiState.asStateFlow()

    var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    fun login(body: LoginRequestModel) = viewModelScope.launch {
        loginUseCase(body = body)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _loginUiState.value = LoginUiState.Loading
                    is Result.Success -> {
                        _loginUiState.value = LoginUiState.Success(result.data)
                        saveToken(result.data)
                    }
                    is Result.Error -> {
                        _loginUiState.value = LoginUiState.Error(result.exception)
                        result.exception.errorHandling(
                            badRequestAction = { _loginUiState.value = LoginUiState.BadRequest },
                            notFoundAction = { _loginUiState.value = LoginUiState.NotFound }
                        )
                    }
                }
            }
    }

    fun saveToken(token: LoginResponseModel) = viewModelScope.launch {
        _saveTokenUiState.value = SaveTokenUiState.Loading
        saveTokenUseCase(token = token)
            .onSuccess {
                _saveTokenUiState.value = SaveTokenUiState.Success
            }.onFailure {
                _saveTokenUiState.value = SaveTokenUiState.Error(it)
            }
    }

    fun onEmailChange(value: String) {
        savedStateHandle[EMAIL] = value
    }

    fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }
}

private const val EMAIL = "email"
private const val PASSWORD = "password"