package com.goms.sign_up.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.network.errorHandling
import com.goms.common.result.Result
import com.goms.domain.auth.SendNumberUseCase
import com.goms.domain.auth.SighUpUseCase
import com.goms.domain.auth.VerifyNumberUseCase
import com.goms.model.request.auth.SendNumberRequest
import com.goms.model.request.auth.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val signUpUseCase: SighUpUseCase,
    private val sendNumberUseCase: SendNumberUseCase,
    private val verifyNumberUseCase: VerifyNumberUseCase
) : ViewModel() {
    private val _signUpUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val signUpUiState = _signUpUiState.asStateFlow()

    private val _sendNumberUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val sendNumberUiState = _sendNumberUiState.asStateFlow()

    private val _verifyNumberUiState = MutableStateFlow<VerifyNumberUiState>(VerifyNumberUiState.Loading)
    val verifyNumberUiState = _verifyNumberUiState.asStateFlow()

    var name = savedStateHandle.getStateFlow(key = NAME, initialValue = "")
    var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    var gender = savedStateHandle.getStateFlow(key = GENDER, initialValue = "")
    var major = savedStateHandle.getStateFlow(key = MAJOR, initialValue = "")
    var number = savedStateHandle.getStateFlow(key = NUMBER, initialValue = "")
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")
    var checkPassword = savedStateHandle.getStateFlow(key = CHECK_PASSWORD, initialValue = "")

    fun signUp(body: SignUpRequest) = viewModelScope.launch {
        signUpUseCase(body = body)
            .onSuccess {
                it.catch {  remoteError ->
                    _signUpUiState.value = Result.Error(remoteError)
                }.collect { result ->
                    _signUpUiState.value = Result.Success(result)
                }
            }.onFailure {
                _signUpUiState.value = Result.Error(it)
            }
    }

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
                    _verifyNumberUiState.value = VerifyNumberUiState.Error(remoteError)
                    remoteError.errorHandling(
                        notFoundAction = { _verifyNumberUiState.value = VerifyNumberUiState.NotFound }
                    )
                }.collect { result ->
                    _verifyNumberUiState.value = VerifyNumberUiState.Success
                }
            }.onFailure {
                _verifyNumberUiState.value = VerifyNumberUiState.Error(it)
                it.errorHandling(
                    notFoundAction = { _verifyNumberUiState.value = VerifyNumberUiState.NotFound }
                )
            }
    }

    fun initVerifyNumber() {
        _verifyNumberUiState.value = VerifyNumberUiState.Loading
    }

    fun onNameChange(value: String) {
        savedStateHandle[NAME] = value
    }

    fun onEmailChange(value: String) {
        savedStateHandle[EMAIL] = value
    }

    fun onGenderChange(value: String) {
        savedStateHandle[GENDER] = value
    }

    fun onMajorChange(value: String) {
        savedStateHandle[MAJOR] = value
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

private const val NAME = "name"
private const val EMAIL = "email"
private const val GENDER = "gender"
private const val MAJOR = "major"
private const val NUMBER = "number"
private const val PASSWORD = "password"
private const val CHECK_PASSWORD = "check password"
