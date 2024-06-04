package com.goms.setting.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.network.errorHandling
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.data.repository.auth.AuthRepository
import com.goms.data.repository.setting.SettingRepository
import com.goms.domain.account.DeleteProfileImageUseCase
import com.goms.domain.account.GetProfileUseCase
import com.goms.domain.account.SetProfileImageUseCase
import com.goms.domain.account.UpdateProfileImageUseCase
import com.goms.domain.account.WithdrawalUseCase
import com.goms.domain.auth.LogoutUseCase
import com.goms.domain.setting.SetAlarmUseCase
import com.goms.domain.setting.SetQrcodeUseCase
import com.goms.domain.setting.SetThemeUseCase
import com.goms.domain.setting.SetTimeUseCase
import com.goms.model.util.Regex.PASSWORD
import com.goms.setting.util.getMultipartFile
import com.goms.setting.viewmodel.uistate.GetProfileUiState
import com.goms.setting.viewmodel.uistate.LogoutUiState
import com.goms.setting.viewmodel.uistate.ProfileImageUiState
import com.goms.setting.viewmodel.uistate.SetThemeUiState
import com.goms.setting.viewmodel.uistate.WithdrawalUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor (
    private val getProfileUseCase: GetProfileUseCase,
    private val setProfileImageUseCase: SetProfileImageUseCase,
    private val updateProfileImageUseCase: UpdateProfileImageUseCase,
    private val deleteProfileImageUseCase: DeleteProfileImageUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val settingRepository: SettingRepository,
    private val setThemeUseCase: SetThemeUseCase,
    private val setQrcodeUseCase: SetQrcodeUseCase,
    private val setAlarmUseCase: SetAlarmUseCase,
    private val setTimeUseCase: SetTimeUseCase,
    private val withdrawalUseCase: WithdrawalUseCase,
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    internal val role = authRepository.getRole()

    internal var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    private val _themeState = MutableStateFlow("")
    internal val themeState = _themeState.asStateFlow()

    private val _qrcodeState = MutableStateFlow("")
    internal val qrcodeState = _qrcodeState.asStateFlow()

    private val _alarmState = MutableStateFlow("")
    internal val alarmState = _alarmState.asStateFlow()

    private val _timeState = MutableStateFlow("")
    internal val timeState = _timeState.asStateFlow()

    private val _setThemeState = MutableStateFlow<SetThemeUiState>(SetThemeUiState.Loading)
    internal val setThemeState = _setThemeState.asStateFlow()

    private val _logoutState = MutableStateFlow<LogoutUiState>(LogoutUiState.Loading)
    internal val logoutState = _logoutState.asStateFlow()

    private val _profileImageUiState = MutableStateFlow<ProfileImageUiState>(ProfileImageUiState.Loading)
    internal val profileImageUiState = _profileImageUiState.asStateFlow()

    private val _getProfileUiState = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Loading)
    internal val getProfileUiState = _getProfileUiState.asStateFlow()

    private val _withdrawalUiState = MutableStateFlow<WithdrawalUiState>(WithdrawalUiState.Loading)
    internal val withdrawalUiState = _withdrawalUiState.asStateFlow()

    internal fun getProfile() = viewModelScope.launch {
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

    internal fun initGetProfile() {
        _getProfileUiState.value = GetProfileUiState.Loading
    }

    internal fun setProfileImage(context: Context, file: Uri) = viewModelScope.launch {
        val multipartFile = getMultipartFile(context, file)

        setProfileImageUseCase(multipartFile!!)
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

    internal fun updateProfileImage(context: Context, file: Uri) = viewModelScope.launch {
        val multipartFile = getMultipartFile(context, file)

        updateProfileImageUseCase(multipartFile!!)
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

    internal fun deleteProfileImage() = viewModelScope.launch {
        deleteProfileImageUseCase()
            .onSuccess {
                it.catch {remoteError ->
                    _profileImageUiState.value = ProfileImageUiState.Error(remoteError)
                    remoteError.errorHandling(
                        notFoundAction = { _profileImageUiState.value = ProfileImageUiState.EmptyProfileUrl }
                    )
                }.collect {
                    _profileImageUiState.value = ProfileImageUiState.Success
                }
            }.onFailure {
                _profileImageUiState.value = ProfileImageUiState.Error(it)
            }
    }

    internal fun initProfileImage() {
        _profileImageUiState.value = ProfileImageUiState.Loading
    }

    internal fun setTheme(theme: String) = viewModelScope.launch {
       setThemeUseCase(theme = theme)
           .onSuccess {
               getThemeValue()
               _setThemeState.value = SetThemeUiState.Success
           }.onFailure {
               _setThemeState.value = SetThemeUiState.Error(it)
           }
    }

    internal fun initSetTheme() {
        _setThemeState.value = SetThemeUiState.Loading
    }

    internal fun setQrcode(qrcode: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            setQrcodeUseCase(qrcode = qrcode)
        }
    }

    internal fun setAlarm(alarm: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            setAlarmUseCase(alarm = alarm)
        }
    }

    internal fun setTime(time: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            setTimeUseCase(time = time)
        }
    }

    internal fun getThemeValue() = viewModelScope.launch {
        val themeValue = settingRepository.getThemeValue().first().replace("\"","")
        _themeState.value = themeValue
    }

    internal fun getQrcodeValue() = viewModelScope.launch {
        val qrcodeValue = settingRepository.getQrcodeValue().first().replace("\"","")
        _qrcodeState.value = qrcodeValue
    }

    internal fun getAlarmValue() = viewModelScope.launch {
        val alarmValue = settingRepository.getAlarmValue().first().replace("\"","")
        _alarmState.value = alarmValue
    }

    internal fun getTimeValue() = viewModelScope.launch {
        val timeValue = settingRepository.getTimeValue().first().replace("\"","")
        _timeState.value = timeValue
    }

    internal fun logout() = viewModelScope.launch {
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

    internal fun withdrawal() = viewModelScope.launch {
        withdrawalUseCase(password.value)
            .onSuccess {
                it.catch { remoteError ->
                    _withdrawalUiState.value = WithdrawalUiState.Error(remoteError)
                    remoteError.errorHandling(
                        badRequestAction = { _withdrawalUiState.value = WithdrawalUiState.BadRequest },
                    )
                }.collect {
                    _withdrawalUiState.value = WithdrawalUiState.Success
                }
            }.onFailure {
                _withdrawalUiState.value = WithdrawalUiState.Error(it)
                it.errorHandling(
                    badRequestAction = { _withdrawalUiState.value = WithdrawalUiState.BadRequest },
                )
            }
    }

    internal fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }

}