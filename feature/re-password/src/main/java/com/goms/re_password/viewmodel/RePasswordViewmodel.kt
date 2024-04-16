package com.goms.re_password.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RePasswordViewmodel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")
    var newPassword = savedStateHandle.getStateFlow(key = NEW_PASSWORD, initialValue = "")
    var newCheckPassword = savedStateHandle.getStateFlow(key = NEW_CHECK_PASSWORD, initialValue = "")

    fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }

    fun onNewPasswordChange(value: String) {
        savedStateHandle[NEW_PASSWORD] = value
    }

    fun onNewCheckPasswordChange(value: String) {
        savedStateHandle[NEW_CHECK_PASSWORD] = value
    }
}

private const val PASSWORD = "password"
private const val NEW_PASSWORD = "new password"
private const val NEW_CHECK_PASSWORD = "new check password"
