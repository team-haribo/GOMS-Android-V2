package com.goms.setting

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.bottomsheet.ProfileBottomSheet
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.dialog.GomsTwoButtonDialog
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.enum.Authority
import com.goms.model.enum.Switch
import com.goms.model.util.ResourceKeys
import com.goms.setting.component.SettingButton
import com.goms.setting.component.SelectThemeDropDown
import com.goms.setting.component.SettingButtonType
import com.goms.setting.component.SettingProfileCard
import com.goms.setting.component.SettingSwitchComponent
import com.goms.setting.data.toData
import com.goms.setting.viewmodel.uistate.GetProfileUiState
import com.goms.setting.viewmodel.uistate.LogoutUiState
import com.goms.setting.viewmodel.uistate.ProfileImageUiState
import com.goms.setting.viewmodel.uistate.SetThemeUiState
import com.goms.setting.viewmodel.SettingViewModel
import com.goms.ui.GomsRoleBackButton
import com.goms.ui.rememberMultiplePermissionsStateSafe
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@Composable
internal fun SettingRoute(
    onBackClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onPasswordCheck: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    onThemeSelect: () -> Unit,
    onUpdateAlarm: (String) -> Unit,
    onWithdrawalClick: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val role by viewModel.role.collectAsStateWithLifecycle(initialValue = ResourceKeys.EMPTY)
    val getProfileUiState by viewModel.getProfileUiState.collectAsStateWithLifecycle()
    val profileImageUiState by viewModel.profileImageUiState.collectAsStateWithLifecycle()
    val logoutUiState by viewModel.logoutState.collectAsStateWithLifecycle()
    val setThemeUiState by viewModel.setThemeState.collectAsStateWithLifecycle()
    val themeState by viewModel.themeState.collectAsStateWithLifecycle()
    val qrcodeState by viewModel.qrcodeState.collectAsStateWithLifecycle()
    val alarmState by viewModel.alarmState.collectAsStateWithLifecycle()
    val timeState by viewModel.timeState.collectAsStateWithLifecycle()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var qrcodeData by remember { mutableStateOf(qrcodeState) }
    var alarmData by remember { mutableStateOf(alarmState) }
    var timeData by remember { mutableStateOf(timeState) }
    var isLoading by remember { mutableStateOf(false) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }

    LaunchedEffect(selectedImageUri) {
        if (selectedImageUri != null && getProfileUiState is GetProfileUiState.Success) {
            val data = (getProfileUiState as GetProfileUiState.Success)
                .getProfileResponseModel
                .toData()

            if (data.profileUrl.isNullOrEmpty()) {
                viewModel.setProfileImage(context, selectedImageUri!!)
                isLoading = true
            } else {
                viewModel.updateProfileImage(context, selectedImageUri!!)
                isLoading = true
            }
        }
    }

    DisposableEffect("setting data set") {
        onDispose {
            if (!qrcodeData.isNullOrEmpty()) {
                viewModel.setQrcode(qrcodeData)
            }

            if (!alarmData.isNullOrEmpty()) {
                viewModel.setAlarm(alarmData)
                onUpdateAlarm(alarmData)
            }

            if (!timeData.isNullOrEmpty()) {
                viewModel.setTime(timeData)
            }
        }
    }

    SettingScreen(
        role = role,
        onProfileClick = { isGallery ->
            if (isGallery) {
                galleryLauncher.launch("image/*")
            } else {
                viewModel.deleteProfileImage()
                isLoading = true
            }
        },
        onBackClick = onBackClick,
        onLogoutClick = viewModel::logout,
        onLogoutSuccess = onLogoutSuccess,
        getProfile = {
            with(viewModel) {
                initProfileImage()
                initGetProfile()
                getProfile()
            }
        },
        getSettingInfo = {
            with(viewModel) {
                getThemeValue()
                getQrcodeValue()
                getAlarmValue()
                getTimeValue()
            }
        },
        onThemeSelect = { selectedTheme ->
            viewModel.initSetTheme()
            viewModel.setTheme(selectedTheme)
        },
        onUpdateTheme = onThemeSelect,
        onUpdateQrcode = { qrcodeData = it },
        onUpdateAlarm = { alarmData = it },
        onUpdateTime = { timeData = it },
        setDefaultProfileUiState = viewModel::initProfileImage,
        isLoading = { isLoading = it },
        onErrorToast = onErrorToast,
        onPasswordCheck = onPasswordCheck,
        onWithdrawalClick = onWithdrawalClick,
        logoutUiState = logoutUiState,
        setThemeUiState = setThemeUiState,
        getProfileUiState = getProfileUiState,
        themeState = themeState,
        qrcodeState = qrcodeState,
        alarmState = alarmState,
        timeState = timeState,
        loadingState = isLoading,
        profileImageUiState = profileImageUiState
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun SettingScreen(
    role: String,
    onProfileClick: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    getProfile: () -> Unit,
    getSettingInfo: () -> Unit,
    onThemeSelect: (String) -> Unit,
    onUpdateTheme: () -> Unit,
    onUpdateQrcode: (String) -> Unit,
    onUpdateAlarm: (String) -> Unit,
    onUpdateTime: (String) -> Unit,
    setDefaultProfileUiState: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    onPasswordCheck: () -> Unit,
    onWithdrawalClick: () -> Unit,
    isLoading: (Boolean) -> Unit,
    logoutUiState: LogoutUiState,
    setThemeUiState: SetThemeUiState,
    getProfileUiState: GetProfileUiState,
    themeState: String,
    qrcodeState: String,
    alarmState: String,
    timeState: String,
    loadingState: Boolean,
    profileImageUiState: ProfileImageUiState
) {
    var openDialog by remember { mutableStateOf(false) }
    var openBottomSheet by remember { mutableStateOf(false) }
    var isLogout by remember { mutableStateOf(true) }

    val notificationPermissionState = rememberMultiplePermissionsStateSafe(listOf(Manifest.permission.POST_NOTIFICATIONS))
    val scrollState = rememberScrollState()

    LaunchedEffect("load data") {
        getProfile()
        getSettingInfo()
    }

    when (logoutUiState) {
        is LogoutUiState.Loading -> Unit
        is LogoutUiState.Success -> {
            onLogoutSuccess()
        }

        is LogoutUiState.Error -> {
            onErrorToast(logoutUiState.exception, R.string.error_logout)
        }
    }

    when (setThemeUiState) {
        is SetThemeUiState.Loading -> Unit
        is SetThemeUiState.Success -> {
            onUpdateTheme()
        }

        is SetThemeUiState.Error -> {
            onErrorToast(null, R.string.error_set_theme)
        }
    }

    when (profileImageUiState) {
        is ProfileImageUiState.Loading -> Unit
        is ProfileImageUiState.Success -> {
            isLoading(false)
            getProfile()
        }

        is ProfileImageUiState.EmptyProfileUrl -> {
            isLoading(false)
            onErrorToast(null, R.string.error_already_default_profile)
            setDefaultProfileUiState()
        }

        is ProfileImageUiState.Error -> {
            isLoading(false)
            onErrorToast(profileImageUiState.exception, R.string.error_profile)
            setDefaultProfileUiState()
        }
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.BACKGROUND)
            .navigationBarsPadding()
            .statusBarsPadding(),
    ) {
        GomsRoleBackButton(role = if (role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT) {
            onBackClick()
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GomsSpacer(size = SpacerSize.Small)
            SettingProfileCard(
                modifier = Modifier,
                role = role,
                onProfileClick = { openBottomSheet = true },
                getProfileUiState = getProfileUiState
            )
            GomsSpacer(size = SpacerSize.Large)
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = colors.WHITE.copy(0.15f)
            )
            GomsSpacer(size = SpacerSize.Medium)
            SelectThemeDropDown(
                modifier = Modifier,
                onThemeSelect = {
                    val selectedTheme = when (it) {
                        0 -> ThemeType.DARK.value
                        1 -> ThemeType.LIGHT.value
                        2 -> ThemeType.SYSTEM.value
                        else -> ThemeType.DARK.value
                    }
                    onThemeSelect(selectedTheme)
                },
                themeState = themeState
            )
            GomsSpacer(size = SpacerSize.Medium)
            if (role == Authority.ROLE_STUDENT.name) {
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    title = stringResource(id = R.string.show_clock),
                    detail = stringResource(id = R.string.show_clock_description),
                    switchOnBackground = colors.P5,
                    switchOffBackground = colors.G4,
                    isSwitchOn = timeState == Switch.ON.value,
                    onFunctionOff = { onUpdateTime(Switch.OFF.value) },
                    onFunctionOn = { onUpdateTime(Switch.ON.value) }
                )
                GomsSpacer(size = SpacerSize.Large)
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    title = stringResource(id = R.string.outing_push_alarm),
                    detail = stringResource(id = R.string.outing_push_alarm_description),
                    switchOnBackground = colors.P5,
                    switchOffBackground = colors.G4,
                    isSwitchOn = alarmState == Switch.ON.value,
                    onFunctionOff = { onUpdateAlarm(Switch.OFF.value) },
                    onFunctionOn = { onUpdateAlarm(Switch.ON.value) }
                )
                GomsSpacer(size = SpacerSize.Large)
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    title = stringResource(id = R.string.right_now_camera),
                    detail = stringResource(id = R.string.right_now_camera_description),
                    switchOnBackground = colors.P5,
                    switchOffBackground = colors.G4,
                    isSwitchOn = qrcodeState == Switch.ON.value,
                    onFunctionOff = { onUpdateQrcode(Switch.OFF.value) },
                    onFunctionOn = { onUpdateQrcode(Switch.ON.value) }
                )
            }
            if (role == Authority.ROLE_STUDENT_COUNCIL.name) {
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    title = stringResource(id = R.string.show_clock),
                    detail = stringResource(id = R.string.show_clock_description),
                    switchOnBackground = colors.A7,
                    switchOffBackground = colors.G4,
                    isSwitchOn = timeState == Switch.ON.value,
                    onFunctionOff = { onUpdateTime(Switch.OFF.value) },
                    onFunctionOn = { onUpdateTime(Switch.ON.value) }
                )
                GomsSpacer(size = SpacerSize.Large)
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    title = stringResource(id = R.string.right_now_qr_create),
                    detail = stringResource(id = R.string.right_now_qr_create_description),
                    switchOnBackground = colors.A7,
                    switchOffBackground = colors.G4,
                    isSwitchOn = qrcodeState == Switch.ON.value,
                    onFunctionOff = { onUpdateQrcode(Switch.OFF.value) },
                    onFunctionOn = { onUpdateQrcode(Switch.ON.value) }
                )
            }
            GomsSpacer(size = SpacerSize.Large)
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = colors.WHITE.copy(0.15f)
            )
            GomsSpacer(size = SpacerSize.Medium)
            SettingButton(
                modifier = Modifier,
                buttonType = SettingButtonType.PasswordChange.value
            ) {
                onPasswordCheck()
            }
            SettingButton(
                modifier = Modifier,
                buttonType = SettingButtonType.Logout.value
            ) {
                isLogout = true
                openDialog = true
            }
            SettingButton(
                modifier = Modifier,
                buttonType = SettingButtonType.Withdrawal.value
            ) {
                isLogout = false
                openDialog = true
            }
        }
    }
    if (loadingState) {
        GomsCircularProgressIndicator()
    }

    if (openDialog) {
        GomsTwoButtonDialog(
            openDialog = openDialog,
            onStateChange = { openDialog = it },
            title = stringResource(id = if(isLogout) R.string.logout else R.string.withdrawal),
            content = stringResource(id = if(isLogout) R.string.want_logout else R.string.want_withdrawal),
            dismissText = stringResource(id = R.string.cancel),
            checkText = stringResource(id = if(isLogout) R.string.logout else R.string.withdrawal),
            onDismissClick = { openDialog = false }
        ) {
            if(isLogout) onLogoutClick() else onWithdrawalClick()
        }
    }
    if (openBottomSheet) {
        ProfileBottomSheet(
            modifier = Modifier.padding(horizontal = 20.dp),
            closeSheet = { openBottomSheet = false },
            onGalleryClick = {
                openBottomSheet = false
                onProfileClick(true)
            },
            onDefaultImageClick = {
                openBottomSheet = false
                onProfileClick(false)
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SettingScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        SettingScreen(
            role = Authority.ROLE_STUDENT.name,
            onProfileClick = {},
            onBackClick = {},
            onLogoutClick = {},
            onLogoutSuccess = {},
            getProfile = {},
            getSettingInfo = {},
            onThemeSelect = {},
            onUpdateTheme = {},
            onUpdateQrcode = {},
            onUpdateAlarm = {},
            onUpdateTime = {},
            setDefaultProfileUiState = {},
            onErrorToast = { _, _ -> },
            onPasswordCheck = {},
            onWithdrawalClick = {},
            isLoading = {},
            logoutUiState = LogoutUiState.Loading,
            setThemeUiState = SetThemeUiState.Loading,
            getProfileUiState = GetProfileUiState.Loading,
            themeState = "GOMS",
            qrcodeState = "GOMS",
            alarmState = "GOMS",
            timeState = "GOMS",
            loadingState = false,
            profileImageUiState = ProfileImageUiState.Loading
        )
    }
}