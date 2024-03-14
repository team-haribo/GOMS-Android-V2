package com.goms.re_password.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.domain.auth.SendNumberUseCase
import com.goms.domain.auth.VerifyNumberUseCase
import com.goms.model.request.auth.SendNumberRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RePasswordViewmodel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val sendNumberUseCase: SendNumberUseCase,
    private val verifyNumberUseCase: VerifyNumberUseCase
) : ViewModel() {
    private val _sendNumberUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val sendNumberUiState = _sendNumberUiState.asStateFlow()

    private val _verifyNumberUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val verifyNumberUiState = _verifyNumberUiState.asStateFlow()

    var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")
    var checkPassword = savedStateHandle.getStateFlow(key = CHECK_PASSWORD, initialValue = "")
    var number = savedStateHandle.getStateFlow(key = NUMBER, initialValue = "")


    fun sendNumber(body: SendNumberRequest) = viewModelScope.launch {
        sendNumberUseCase(body = body)
            .onSuccess {
                it.catch {  remoteError ->
                    _sendNumberUiState.value = Result.Error(remoteError)
                }.collect { result ->
                    _sendNumberUiState.value = Result.Success(result)
                }
            }.onFailure {
                _sendNumberUiState.value = Result.Error(it)
            }
    }

    fun initSendNumber() {
        _sendNumberUiState.value = Result.Loading
    }

    fun verifyNumber(email: String, authCode: String) = viewModelScope.launch {
        verifyNumberUseCase(email = email, authCode = authCode)
            .onSuccess {
                it.catch {  remoteError ->
                    _verifyNumberUiState.value = Result.Error(remoteError)
                }.collect { result ->
                    _verifyNumberUiState.value = Result.Success(result)
                }
            }.onFailure {
                _verifyNumberUiState.value = Result.Error(it)
            }
    }

    fun initVerifyNumber() {
        _verifyNumberUiState.value = Result.Loading
    }
    fun onEmailChange(value: String) {
        savedStateHandle[EMAIL] = value
    }
    fun onNumberChange(value: String) {
        savedStateHandle[NUMBER] = value
    }
    fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }
    fun onCheckPasswordChange(value: String) {
        savedStateHandle[CHECK_PASSWORD] = value
    }
}
private const val EMAIL = "email"
private const val PASSWORD = "password"
private const val CHECK_PASSWORD = "check password"
private const val NUMBER = "number"