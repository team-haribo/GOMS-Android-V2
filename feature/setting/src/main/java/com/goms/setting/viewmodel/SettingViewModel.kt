package com.goms.setting.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.datastore.AuthTokenDataSource
import com.goms.domain.account.GetProfileUseCase
import com.goms.domain.account.UploadProfileImageUseCase
import com.goms.domain.auth.LogoutUseCase
import com.goms.setting.util.getMultipartFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor (
    private val authTokenDataSource: AuthTokenDataSource,
    private val getProfileUseCase: GetProfileUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    val role = authTokenDataSource.getAuthority()

    private val _logoutState = MutableStateFlow<LogoutUiState>(LogoutUiState.Loading)
    val logoutState = _logoutState.asStateFlow()

    private val _profileImageUiState = MutableStateFlow<ProfileImageUiState>(ProfileImageUiState.Loading)
    val profileImageUiState = _profileImageUiState.asStateFlow()

    private val _getProfileUiState = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Loading)
    val getProfileUiState = _getProfileUiState.asStateFlow()
    fun getProfile() = viewModelScope.launch {
        getProfileUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getProfileUiState.value = GetProfileUiState.Loading
                    is Result.Success -> {
                        _getProfileUiState.value = GetProfileUiState.Success(result.data)
                    }
                    is Result.Error -> _getProfileUiState.value = GetProfileUiState.Error(result.exception)
                }
            }
    }

    fun initGetProfile() {
        _getProfileUiState.value = GetProfileUiState.Loading
    }

    fun uploadProfileImage(context: Context, file: Uri) = viewModelScope.launch {
        val multipartFile = getMultipartFile(context, file)

        uploadProfileImageUseCase(multipartFile!!)
            .onSuccess {
                it.catch {remoteError ->
                    _profileImageUiState.value = ProfileImageUiState.Error(remoteError)
                }.collect {
                    _profileImageUiState.value = ProfileImageUiState.Success
                }
            }.onFailure {
                _profileImageUiState.value = ProfileImageUiState.Error(it)
            }
    }

    fun initProfileImage() {
        _profileImageUiState.value = ProfileImageUiState.Loading
    }


    fun logout() = viewModelScope.launch {
        logoutUseCase()
            .onSuccess {
                it.catch {  remoteError ->
                    _logoutState.value = LogoutUiState.Error(remoteError)
                }.collect {
                    _logoutState.value = LogoutUiState.Success
                }
            }.onFailure {
                _logoutState.value = LogoutUiState.Error(it)
            }
    }
}