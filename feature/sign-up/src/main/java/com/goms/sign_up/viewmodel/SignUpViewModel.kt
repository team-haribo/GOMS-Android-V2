package com.goms.sign_up.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.network.errorHandling
import com.goms.domain.auth.SendNumberUseCase
import com.goms.domain.auth.SighUpUseCase
import com.goms.domain.auth.VerifyNumberUseCase
import com.goms.model.request.auth.SendNumberRequestModel
import com.goms.model.request.auth.SignUpRequestModel
import com.goms.model.util.ResourceKeys
import com.goms.sign_up.viewmodel.uistate.SendNumberUiState
import com.goms.sign_up.viewmodel.uistate.SignUpUiState
import com.goms.sign_up.viewmodel.uistate.VerifyNumberUiState
import com.goms.ui.isValidEmail
import com.goms.ui.isValidPassword
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
    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading)
    internal val signUpUiState = _signUpUiState.asStateFlow()

    private val _sendNumberUiState = MutableStateFlow<SendNumberUiState>(SendNumberUiState.Loading)
    internal val sendNumberUiState = _sendNumberUiState.asStateFlow()

    private val _verifyNumberUiState = MutableStateFlow<VerifyNumberUiState>(VerifyNumberUiState.Loading)
    internal val verifyNumberUiState = _verifyNumberUiState.asStateFlow()

    internal var name = savedStateHandle.getStateFlow(key = NAME, initialValue = ResourceKeys.EMPTY)
    internal var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = ResourceKeys.EMPTY)
    internal var gender = savedStateHandle.getStateFlow(key = GENDER, initialValue = ResourceKeys.EMPTY)
    internal var major = savedStateHandle.getStateFlow(key = MAJOR, initialValue = ResourceKeys.EMPTY)
    internal var number = savedStateHandle.getStateFlow(key = NUMBER, initialValue = ResourceKeys.EMPTY)
    internal var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = ResourceKeys.EMPTY)
    internal var checkPassword = savedStateHandle.getStateFlow(key = CHECK_PASSWORD, initialValue = ResourceKeys.EMPTY)

    internal fun signUp(body: SignUpRequestModel) = viewModelScope.launch {
        _signUpUiState.value = SignUpUiState.Loading
        when {
            password.value != checkPassword.value -> {
                _signUpUiState.value = SignUpUiState.PasswordMismatch
            }

            !isValidPassword(body.password) -> {
                _signUpUiState.value = SignUpUiState.PasswordNotValid
            }

            else -> {
                signUpUseCase(body = body)
                    .onSuccess {
                        it.catch {  remoteError ->
                            _signUpUiState.value = SignUpUiState.Error(remoteError)
                            remoteError.errorHandling(
                                conflictAction = { _signUpUiState.value = SignUpUiState.Conflict }
                            )
                        }.collect { result ->
                            _signUpUiState.value = SignUpUiState.Success
                        }
                    }.onFailure {
                        _signUpUiState.value = SignUpUiState.Error(it)
                        it.errorHandling(
                            conflictAction = { _signUpUiState.value = SignUpUiState.Conflict }
                        )
                    }
            }
        }
    }

    internal fun initSignUp() {
        _signUpUiState.value = SignUpUiState.Loading
    }

    internal fun sendNumber(body: SendNumberRequestModel) = viewModelScope.launch {
        if (!isValidEmail(body.email)) {
            _sendNumberUiState.value = SendNumberUiState.EmailNotValid
        } else {
            sendNumberUseCase(body = body)
                .onSuccess {
                    it.catch {  remoteError ->
                        _sendNumberUiState.value = SendNumberUiState.Error(remoteError)
                        remoteError.errorHandling(
                            tooManyRequestAction = { _sendNumberUiState.value = SendNumberUiState.TooManyRequest }
                        )
                    }.collect { result ->
                        _sendNumberUiState.value = SendNumberUiState.Success
                    }
                }.onFailure {
                    _sendNumberUiState.value = SendNumberUiState.Error(it)
                    it.errorHandling(
                        tooManyRequestAction = { _sendNumberUiState.value = SendNumberUiState.TooManyRequest }
                    )
                }
        }
    }

    internal fun initSendNumber() {
        _sendNumberUiState.value = SendNumberUiState.Loading
    }

    internal fun verifyNumber(email: String, authCode: String) = viewModelScope.launch {
        verifyNumberUseCase(email = email, authCode = authCode)
            .onSuccess {
                it.catch {  remoteError ->
                    _verifyNumberUiState.value = VerifyNumberUiState.Error(remoteError)
                    remoteError.errorHandling(
                        badRequestAction = { _verifyNumberUiState.value = VerifyNumberUiState.BadRequest },
                        notFoundAction = { _verifyNumberUiState.value = VerifyNumberUiState.NotFound }
                    )
                }.collect { result ->
                    _verifyNumberUiState.value = VerifyNumberUiState.Success
                }
            }.onFailure {
                _verifyNumberUiState.value = VerifyNumberUiState.Error(it)
                it.errorHandling(
                    badRequestAction = { _verifyNumberUiState.value = VerifyNumberUiState.BadRequest },
                    notFoundAction = { _verifyNumberUiState.value = VerifyNumberUiState.NotFound },
                    tooManyRequestAction = { _verifyNumberUiState.value = VerifyNumberUiState.TooManyRequest }
                )
            }
    }

    internal fun initVerifyNumber() {
        _verifyNumberUiState.value = VerifyNumberUiState.Loading
    }

    internal fun onNameChange(value: String) {
        savedStateHandle[NAME] = value
    }

    internal fun onEmailChange(value: String) {
        savedStateHandle[EMAIL] = value
    }

    internal fun onGenderChange(value: String) {
        savedStateHandle[GENDER] = value
    }

    internal fun onMajorChange(value: String) {
        savedStateHandle[MAJOR] = value
    }

    internal fun onNumberChange(value: String) {
        savedStateHandle[NUMBER] = value
    }

    internal fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }

    internal fun onCheckPasswordChange(value: String) {
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
