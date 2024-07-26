package com.goms.main

import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.icon.MenuIcon
import com.goms.design_system.icon.SettingIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.ThemeDevicePreviews
import com.goms.main.viewmodel.uistate.GetLateRankListUiState
import com.goms.main.viewmodel.uistate.GetOutingCountUiState
import com.goms.main.viewmodel.uistate.GetOutingListUiState
import com.goms.main.viewmodel.uistate.GetProfileUiState
import com.goms.model.enum.Authority
import com.goms.ui.GomsTopBar
import com.goms.ui.GomsFloatingButton
import com.goms.main.component.MainLateCard
import com.goms.main.component.MainOutingCard
import com.goms.main.component.MainProfileCard
import com.goms.main.component.MainTimeProfileCard
import com.goms.main.viewmodel.MainViewModel
import com.goms.main.viewmodel.uistate.SaveTokenUiState
import com.goms.main.viewmodel.uistate.TokenRefreshUiState
import com.goms.model.enum.Switch
import com.goms.model.util.ResourceKeys
import com.goms.ui.rememberMultiplePermissionsStateSafe
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
internal fun MainRoute(
    qrcodeState: String,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onStudentManagementClick: () -> Unit,
    onQrcodeClick: (role: Authority) -> Unit,
    onSettingClick: () -> Unit,
    onAdminMenuClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val role by viewModel.role.collectAsStateWithLifecycle(initialValue = ResourceKeys.EMPTY)
    val timeValue by viewModel.timeValue.collectAsStateWithLifecycle(initialValue = Switch.OFF.value)
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val tokenRefreshUiState by viewModel.tokenRefreshUiState.collectAsStateWithLifecycle()
    val saveTokenUiState by viewModel.saveTokenUiState.collectAsStateWithLifecycle()
    val getProfileUiState by viewModel.getProfileUiState.collectAsStateWithLifecycle()
    val getLateRankListUiState by viewModel.getLateRankListUiState.collectAsStateWithLifecycle()
    val getOutingListUiState by viewModel.getOutingListUiState.collectAsStateWithLifecycle()
    val getOutingCountUiState by viewModel.getOutingCountUiState.collectAsStateWithLifecycle()

    var isQrcodeLaunch by rememberSaveable { mutableStateOf(true) }
    var isTimeLaunch by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(role, timeValue) {
        if (qrcodeState == Switch.ON.value && isQrcodeLaunch && role.isNotBlank()) {
            onQrcodeClick(Authority.valueOf(role))
            isQrcodeLaunch = false
        }

        isTimeLaunch = timeValue == Switch.ON.value
    }

    MainScreen(
        role = if (role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT,
        isRefreshing = isRefreshing,
        isTimeLaunch = isTimeLaunch,
        tokenRefreshUiState = tokenRefreshUiState,
        saveTokenUiState = saveTokenUiState,
        getProfileUiState = getProfileUiState,
        getLateRankListUiState = getLateRankListUiState,
        getOutingListUiState = getOutingListUiState,
        getOutingCountUiState = getOutingCountUiState,
        onOutingStatusClick = onOutingStatusClick,
        onLateListClick = onLateListClick,
        onStudentManagementClick = onStudentManagementClick,
        onQrcodeClick = onQrcodeClick,
        onSettingClick = onSettingClick,
        onAdminMenuClick = onAdminMenuClick,
        onErrorToast = onErrorToast,
        mainCallBack = {
            with(viewModel) {
                getProfile()
                getLateRankList()
                getOutingCount()
                getTimeValue()
            }
        },
        tokenRefreshCallBack = viewModel::tokenRefresh,
        initTokenRefreshCallBack = viewModel::initTokenRefresh
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun MainScreen(
    role: Authority,
    isRefreshing: Boolean,
    isTimeLaunch: Boolean,
    tokenRefreshUiState: TokenRefreshUiState,
    saveTokenUiState: SaveTokenUiState,
    getProfileUiState: GetProfileUiState,
    getLateRankListUiState: GetLateRankListUiState,
    getOutingListUiState: GetOutingListUiState,
    getOutingCountUiState: GetOutingCountUiState,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onStudentManagementClick: () -> Unit,
    onQrcodeClick: (role: Authority) -> Unit,
    onSettingClick: () -> Unit,
    onAdminMenuClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    mainCallBack: () -> Unit,
    tokenRefreshCallBack: () -> Unit,
    initTokenRefreshCallBack: () -> Unit
) {
    var isPermissionRequest by rememberSaveable { mutableStateOf(false) }
    val multiplePermissionState = rememberMultiplePermissionsStateSafe(
        listOf(
            Manifest.permission.CAMERA,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            },
            Manifest.permission.POST_NOTIFICATIONS
        )
    )

    if (!isPermissionRequest) {
        LaunchedEffect("getPermission") {
            if (!multiplePermissionState.allPermissionsGranted) {
                multiplePermissionState.launchMultiplePermissionRequest()
            }
        }
        isPermissionRequest = true
    }

    LaunchedEffect(true) {
        mainCallBack()
    }

    DisposableEffect(tokenRefreshUiState) {
        if (tokenRefreshUiState is TokenRefreshUiState.Error) {
            onErrorToast(null, R.string.error_refresh)
        }
        onDispose { initTokenRefreshCallBack() }
    }

    val scrollState = rememberScrollState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    var currentTime by rememberSaveable { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect("Time") {
        while (true) {
            delay(1_000L)
            currentTime = System.currentTimeMillis()
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        modifier = Modifier.statusBarsPadding(),
        onRefresh = {
            tokenRefreshCallBack()
        },
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                backgroundColor = colors.G1,
                contentColor = colors.WHITE
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .background(colors.BACKGROUND)
        ) {
            Column {
                GomsTopBar(
                    icon = {
                        if (role == Authority.ROLE_STUDENT_COUNCIL) MenuIcon(tint = colors.G4)
                        else SettingIcon(tint = colors.G4)
                    },
                    onSettingClick = {
                        if (role == Authority.ROLE_STUDENT_COUNCIL) onAdminMenuClick()
                        else onSettingClick()
                    }
                )
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    if (isTimeLaunch) {
                        MainTimeProfileCard(
                            time = currentTime,
                            getProfileUiState = getProfileUiState,
                            onErrorToast = onErrorToast
                        )
                    } else {
                        MainProfileCard(
                            getProfileUiState = getProfileUiState,
                            onErrorToast = onErrorToast
                        )
                    }
                    MainLateCard(
                        role = role,
                        getLateRankListUiState = getLateRankListUiState,
                        onErrorToast = onErrorToast
                    ) {
                        onLateListClick()
                    }
                }
                Column(modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)) {
                    MainOutingCard(
                        role = role,
                        getOutingListUiState = getOutingListUiState,
                        getOutingCountUiState = getOutingCountUiState,
                        onErrorToast = onErrorToast
                    ) {
                        onOutingStatusClick()
                    }
                }
            }
            GomsFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp),
                role = role
            ) {
                onQrcodeClick(role)
            }
        }
    }
}

@ThemeDevicePreviews
@Composable
private fun MainScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        MainScreen(
            role = Authority.ROLE_STUDENT_COUNCIL,
            isRefreshing = false,
            isTimeLaunch = false,
            tokenRefreshUiState = TokenRefreshUiState.Loading,
            saveTokenUiState = SaveTokenUiState.Loading,
            getProfileUiState = GetProfileUiState.Loading,
            getLateRankListUiState = GetLateRankListUiState.Loading,
            getOutingListUiState = GetOutingListUiState.Loading,
            getOutingCountUiState = GetOutingCountUiState.Loading,
            onOutingStatusClick = {},
            onLateListClick = {},
            onStudentManagementClick = {},
            onQrcodeClick = {},
            onSettingClick = {},
            onAdminMenuClick = {},
            onErrorToast = { _, _ -> },
            mainCallBack = {},
            tokenRefreshCallBack = {},
        ) {}
    }
}
