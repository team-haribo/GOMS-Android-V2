package com.goms.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.data.repository.auth.AuthRepository
import com.goms.data.repository.setting.SettingRepository
import com.goms.domain.account.GetProfileUseCase
import com.goms.domain.auth.SaveTokenUseCase
import com.goms.domain.auth.TokenRefreshUseCase
import com.goms.domain.council.ChangeAuthorityUseCase
import com.goms.domain.council.DeleteBlackListUseCase
import com.goms.domain.council.DeleteOutingUseCase
import com.goms.domain.council.GetLateListUseCase
import com.goms.domain.council.GetStudentListUseCase
import com.goms.domain.council.SetBlackListUseCase
import com.goms.domain.council.StudentSearchUseCase
import com.goms.domain.late.GetLateRankListUseCase
import com.goms.domain.outing.GetOutingCountUseCase
import com.goms.domain.outing.GetOutingListUseCase
import com.goms.domain.outing.OutingSearchUseCase
import com.goms.main.viewmodel.uistate.GetLateListUiState
import com.goms.main.viewmodel.uistate.GetLateRankListUiState
import com.goms.main.viewmodel.uistate.GetOutingCountUiState
import com.goms.main.viewmodel.uistate.GetOutingListUiState
import com.goms.main.viewmodel.uistate.GetProfileUiState
import com.goms.main.viewmodel.uistate.GetStudentListUiState
import com.goms.main.viewmodel.uistate.OutingSearchUiState
import com.goms.main.viewmodel.uistate.SaveTokenUiState
import com.goms.main.viewmodel.uistate.StudentSearchUiState
import com.goms.main.viewmodel.uistate.TokenRefreshUiState
import com.goms.model.request.council.AuthorityRequestModel
import com.goms.model.response.auth.LoginResponseModel
import com.goms.model.util.ResourceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getLateRankListUseCase: GetLateRankListUseCase,
    private val getOutingListUseCase: GetOutingListUseCase,
    private val getOutingCountUseCase: GetOutingCountUseCase,
    private val outingSearchUseCase: OutingSearchUseCase,
    private val deleteOutingUseCase: DeleteOutingUseCase,
    private val getLateListUseCase: GetLateListUseCase,
    private val getStudentListUseCase: GetStudentListUseCase,
    private val changeAuthorityUseCase: ChangeAuthorityUseCase,
    private val setBlackListUseCase: SetBlackListUseCase,
    private val deleteBlackListUseCase: DeleteBlackListUseCase,
    private val studentSearchUseCase: StudentSearchUseCase,
    private val authRepository: AuthRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {
    internal var role = authRepository.getRole()
    private var refreshToken = runBlocking { authRepository.getRefreshToken().first() }

    private val _isRefreshing = MutableStateFlow(false)
    internal val isRefreshing = _isRefreshing.asStateFlow()

    private val _timeValue = MutableStateFlow(ResourceKeys.EMPTY)
    internal val timeValue = _timeValue.asStateFlow()

    private val _tokenRefreshUiState = MutableStateFlow<TokenRefreshUiState>(TokenRefreshUiState.Loading)
    internal val tokenRefreshUiState = _tokenRefreshUiState.asStateFlow()

    private val _saveTokenUiState = MutableStateFlow<SaveTokenUiState>(SaveTokenUiState.Loading)
    internal val saveTokenUiState = _saveTokenUiState.asStateFlow()

    private val _getProfileUiState = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Loading)
    internal val getProfileUiState = _getProfileUiState.asStateFlow()

    private val _getLateRankListUiState = MutableStateFlow<GetLateRankListUiState>(GetLateRankListUiState.Loading)
    internal val getLateRankListUiState = _getLateRankListUiState.asStateFlow()

    private val _getOutingListUiState = MutableStateFlow<GetOutingListUiState>(GetOutingListUiState.Loading)
    internal val getOutingListUiState = _getOutingListUiState.asStateFlow()

    private val _getOutingCountUiState = MutableStateFlow<GetOutingCountUiState>(GetOutingCountUiState.Loading)
    internal val getOutingCountUiState = _getOutingCountUiState.asStateFlow()

    private val _outingSearchUiState = MutableStateFlow<OutingSearchUiState>(OutingSearchUiState.Loading)
    internal val outingSearchUiState = _outingSearchUiState.asStateFlow()

    private val _deleteOutingUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    internal val deleteOutingUiState = _deleteOutingUiState.asStateFlow()

    private val _getLateListUiState = MutableStateFlow<GetLateListUiState>(GetLateListUiState.Loading)
    internal val getLateListUiState = _getLateListUiState.asStateFlow()

    private val _getStudentListUiState = MutableStateFlow<GetStudentListUiState>(GetStudentListUiState.Loading)
    internal val getStudentListUiState = _getStudentListUiState.asStateFlow()

    private val _changeAuthorityUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    internal val changeAuthorityUiState = _changeAuthorityUiState.asStateFlow()

    private val _setBlackListUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    internal val setBlackListUiState = _setBlackListUiState.asStateFlow()

    private val _deleteBlackListUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    internal val deleteBlackListUiState = _deleteBlackListUiState.asStateFlow()

    private val _studentSearchUiState = MutableStateFlow<StudentSearchUiState>(StudentSearchUiState.Loading)
    internal val studentSearchUiState = _studentSearchUiState.asStateFlow()

    internal var outingSearch = savedStateHandle.getStateFlow(key = OUTING_SEARCH, initialValue = ResourceKeys.EMPTY)
    internal var studentSearch = savedStateHandle.getStateFlow(key = STUDENT_SEARCH, initialValue = ResourceKeys.EMPTY)
    internal var outingState = savedStateHandle.getStateFlow(key = OUTING_STATE, initialValue = ResourceKeys.EMPTY)
    internal var roleState = savedStateHandle.getStateFlow(key = ROLE_STATE, initialValue = ResourceKeys.EMPTY)
    internal var filterStatus = savedStateHandle.getStateFlow(key = FILTER_STATUS, initialValue = ResourceKeys.EMPTY)
    internal var filterGrade = savedStateHandle.getStateFlow(key = FILTER_GRADE, initialValue = ResourceKeys.EMPTY)
    internal var filterGender = savedStateHandle.getStateFlow(key = FILTER_GENDER, initialValue = ResourceKeys.EMPTY)
    internal var filterMajor = savedStateHandle.getStateFlow(key = FILTER_MAJOR, initialValue = ResourceKeys.EMPTY)

    internal fun getTimeValue() = viewModelScope.launch {
        settingRepository.getTimeValue().distinctUntilChanged().collect {
            _timeValue.value = it
        }
    }

    internal fun tokenRefresh() = viewModelScope.launch {
        _isRefreshing.value = true
        delay(1_000L)
        refreshToken = runBlocking { authRepository.getRefreshToken().first() }
        tokenRefreshUseCase(refreshToken = "${ResourceKeys.BEARER} $refreshToken")
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _tokenRefreshUiState.value = TokenRefreshUiState.Loading
                    is Result.Success -> {
                        _tokenRefreshUiState.value = TokenRefreshUiState.Success(result.data)
                        saveToken(token = result.data)
                    }
                    is Result.Error -> {
                        _tokenRefreshUiState.value = TokenRefreshUiState.Error(result.exception)
                        _isRefreshing.value = false
                    }
                }
            }
    }

    internal fun initTokenRefresh() {
        _tokenRefreshUiState.value = TokenRefreshUiState.Loading
    }

    private fun saveToken(token: LoginResponseModel) = viewModelScope.launch {
        _saveTokenUiState.value = SaveTokenUiState.Loading
        saveTokenUseCase(token = token)
            .onSuccess {
                _saveTokenUiState.value = SaveTokenUiState.Success
                role = authRepository.getRole()
                refreshToken = runBlocking { authRepository.getRefreshToken().first() }
                getProfile()
                getLateRankList()
                getOutingCount()
                getTimeValue()
                _isRefreshing.value = false
            }.onFailure {
                _saveTokenUiState.value = SaveTokenUiState.Error(it)
                _isRefreshing.value = false
            }
    }

    internal fun getProfile() = viewModelScope.launch {
        getProfileUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getProfileUiState.value = GetProfileUiState.Loading
                    is Result.Success -> _getProfileUiState.value = GetProfileUiState.Success(result.data)
                    is Result.Error -> _getProfileUiState.value = GetProfileUiState.Error(result.exception)
                }
            }
    }

    internal fun getLateRankList() = viewModelScope.launch {
        getLateRankListUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getLateRankListUiState.value = GetLateRankListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getLateRankListUiState.value = GetLateRankListUiState.Empty
                        } else {
                            _getLateRankListUiState.value = GetLateRankListUiState.Success(result.data)
                        }
                    }
                    is Result.Error -> _getLateRankListUiState.value = GetLateRankListUiState.Error(result.exception)
                }
            }
    }

    private fun getOutingList() = viewModelScope.launch {
        getOutingListUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getOutingListUiState.value = GetOutingListUiState.Loading
                    is Result.Success -> _getOutingListUiState.value = GetOutingListUiState.Success(result.data)
                    is Result.Error -> _getOutingListUiState.value = GetOutingListUiState.Error(result.exception)
                }
            }
    }

    internal fun getOutingCount() = viewModelScope.launch {
        getOutingCountUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getOutingCountUiState.value = GetOutingCountUiState.Loading
                    is Result.Success -> {
                        if (result.data.outingCount == 0) {
                            _getOutingCountUiState.value = GetOutingCountUiState.Empty
                        } else {
                            _getOutingCountUiState.value = GetOutingCountUiState.Success(result.data)
                            getOutingList()
                        }
                    }
                    is Result.Error -> _getOutingCountUiState.value = GetOutingCountUiState.Error(result.exception)
                }
            }
    }

    internal fun outingSearch(name: String) = viewModelScope.launch {
            if (name.isEmpty()) {
                _outingSearchUiState.value = OutingSearchUiState.QueryEmpty
            } else {
                outingSearchUseCase(name = name)
                    .asResult()
                    .collectLatest { result ->
                        when (result) {
                            is Result.Loading -> _outingSearchUiState.value = OutingSearchUiState.Loading
                            is Result.Success -> {
                                if (result.data.isEmpty()) {
                                    _outingSearchUiState.value = OutingSearchUiState.Empty
                                } else {
                                    _outingSearchUiState.value = OutingSearchUiState.Success(result.data)
                                }
                            }
                            is Result.Error -> _outingSearchUiState.value = OutingSearchUiState.Error(result.exception)
                        }
                    }
            }
    }

    internal fun deleteOuting(accountIdx: UUID) = viewModelScope.launch {
        deleteOutingUseCase(accountIdx = accountIdx)
            .onSuccess {
                it.catch {  remoteError ->
                    _deleteOutingUiState.value = Result.Error(remoteError)
                }.collect { result ->
                    _deleteOutingUiState.value = Result.Success(result)
                }
            }.onFailure {
                _deleteOutingUiState.value = Result.Error(it)
            }
    }

    internal fun initDeleteOuting() {
        _deleteOutingUiState.value = Result.Loading
    }

    internal fun getLateList(date: LocalDate) = viewModelScope.launch {
        getLateListUseCase(date = date)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getLateListUiState.value = GetLateListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getLateListUiState.value = GetLateListUiState.Empty
                        } else {
                            _getLateListUiState.value = GetLateListUiState.Success(result.data)
                        }
                    }
                    is Result.Error -> _getLateListUiState.value = GetLateListUiState.Error(result.exception)
                }
            }
    }

    internal fun getStudentList() = viewModelScope.launch {
        getStudentListUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getStudentListUiState.value = GetStudentListUiState.Loading
                    is Result.Success -> _getStudentListUiState.value = GetStudentListUiState.Success(result.data)
                    is Result.Error -> _getStudentListUiState.value = GetStudentListUiState.Error(result.exception)
                }
            }
    }

    internal fun changeAuthority(body: AuthorityRequestModel) = viewModelScope.launch {
        changeAuthorityUseCase(body = body)
            .onSuccess {
                it.catch {  remoteError ->
                    _changeAuthorityUiState.value = Result.Error(remoteError)
                }.collect { result ->
                    _changeAuthorityUiState.value = Result.Success(result)
                }
            }.onFailure {
                _changeAuthorityUiState.value = Result.Error(it)
            }
    }

    internal fun initChangeAuthority() {
        _changeAuthorityUiState.value = Result.Loading
    }

    internal fun setBlackList(accountIdx: UUID) = viewModelScope.launch {
        setBlackListUseCase(accountIdx = accountIdx)
            .onSuccess {
                it.catch {  remoteError ->
                    _setBlackListUiState.value = Result.Error(remoteError)
                }.collect { result ->
                    _setBlackListUiState.value = Result.Success(result)
                }
            }.onFailure {
                _setBlackListUiState.value = Result.Error(it)
            }
    }

    internal fun initSetBlackList() {
        _setBlackListUiState.value = Result.Loading
    }

    internal fun deleteBlackList(accountIdx: UUID) = viewModelScope.launch {
        deleteBlackListUseCase(accountIdx = accountIdx)
            .onSuccess {
                it.catch {  remoteError ->
                    _setBlackListUiState.value = Result.Error(remoteError)
                }.collect { result ->
                    _setBlackListUiState.value = Result.Success(result)
                }
            }.onFailure {
                _setBlackListUiState.value = Result.Error(it)
            }
    }

    internal fun initDeleteBlackList() {
        _deleteBlackListUiState.value = Result.Loading
    }

    internal fun studentSearch(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ) = viewModelScope.launch {
        if (name.isNullOrEmpty() && gender.isNullOrEmpty() && major.isNullOrEmpty() && authority.isNullOrEmpty() && grade == null && isBlackList == null) {
            _studentSearchUiState.value = StudentSearchUiState.QueryEmpty
        } else {
            studentSearchUseCase(
                grade = grade,
                gender = gender,
                major = major,
                name = name,
                isBlackList = isBlackList,
                authority = authority
            )
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> _studentSearchUiState.value = StudentSearchUiState.Loading
                        is Result.Success -> {
                            if (result.data.isEmpty()) {
                                _studentSearchUiState.value = StudentSearchUiState.Empty
                            } else {
                                _studentSearchUiState.value = StudentSearchUiState.Success(result.data)
                            }
                        }
                        is Result.Error -> _studentSearchUiState.value = StudentSearchUiState.Error(result.exception)
                    }
                }
        }
    }

    internal fun onOutingSearchChange(value: String) {
        savedStateHandle[OUTING_SEARCH] = value
    }

    internal fun onStudentSearchChange(value: String) {
        savedStateHandle[STUDENT_SEARCH] = value
    }

    internal fun onOutingStateChange(value: String) {
        savedStateHandle[OUTING_STATE] = value
    }

    internal fun onRoleStateChange(value: String) {
        savedStateHandle[ROLE_STATE] = value
    }

    internal fun onFilterStatusChange(value: String) {
        savedStateHandle[FILTER_STATUS] = value
    }

    internal fun onFilterGradeChange(value: String) {
        savedStateHandle[FILTER_GRADE] = value
    }

    internal fun onFilterGenderChange(value: String) {
        savedStateHandle[FILTER_GENDER] = value
    }

    internal fun onFilterMajorChange(value: String) {
        savedStateHandle[FILTER_MAJOR] = value
    }
}

private const val OUTING_SEARCH = "outing search"
private const val STUDENT_SEARCH = "student search"
private const val OUTING_STATE = "outing state"
private const val ROLE_STATE = "role state"
private const val FILTER_STATUS = "filter status"
private const val FILTER_GRADE = "filter grade"
private const val FILTER_GENDER = "filter gender"
private const val FILTER_MAJOR = "filter major"