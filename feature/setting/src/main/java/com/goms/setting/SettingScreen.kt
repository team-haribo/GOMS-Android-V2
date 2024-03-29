package com.goms.setting

import android.content.pm.ActivityInfo
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.dialog.GomsTwoButtonDialog
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.enum.Authority
import com.goms.setting.component.PasswordChangeButton
import com.goms.setting.component.SelectThemeDropDown
import com.goms.setting.component.SettingProfileCard
import com.goms.setting.component.SettingSwitchComponent
import com.goms.setting.viewmodel.GetProfileUiState
import com.goms.setting.viewmodel.LogoutUiState
import com.goms.setting.viewmodel.ProfileImageUiState
import com.goms.setting.viewmodel.SetThemeUiState
import com.goms.setting.viewmodel.SettingViewModel
import com.goms.ui.GomsRoleBackButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
fun SettingRoute(
    onBackClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onEmailCheck: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    onThemeSelect: () -> Unit,
    onUpdateAlarm: (String) -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val role by viewModel.role.collectAsStateWithLifecycle(initialValue = "")
    val getProfileUiState by viewModel.getProfileUiState.collectAsStateWithLifecycle()
    val profileImageUiState by viewModel.profileImageUiState.collectAsStateWithLifecycle()
    val themeState by viewModel.themeState.collectAsStateWithLifecycle()
    val qrcodeState by viewModel.qrcodeState.collectAsStateWithLifecycle()
    val alarmState by viewModel.alarmState.collectAsStateWithLifecycle()
    val logoutUiState by viewModel.logoutState.collectAsStateWithLifecycle()
    val setThemeUiState by viewModel.setThemeState.collectAsStateWithLifecycle()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var qrcodeData by remember { mutableStateOf("") }
    var alarmData by remember { mutableStateOf("") }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }

    LaunchedEffect(selectedImageUri) {
        if (selectedImageUri != null) {
            viewModel.uploadProfileImage(context, selectedImageUri!!)
        }
    }

    DisposableEffect("qrcode set") {
        onDispose {
            if (!qrcodeData.isNullOrEmpty()) {
                viewModel.setQrcode(qrcodeData)
            }

            if (!alarmData.isNullOrEmpty()) {
                viewModel.setAlarm(alarmData)
                onUpdateAlarm(alarmData)
            }
        }
    }

    SettingScreen(
        role = role,
        onProfileClick = { galleryLauncher.launch("image/*") },
        onBackClick = onBackClick,
        onLogoutClick = { viewModel.logout() },
        onLogoutSuccess = onLogoutSuccess,
        getProfile = {
            viewModel.initProfileImage()
            viewModel.initGetProfile()
            viewModel.getProfile()
        },
        getSettingInfo = {
            viewModel.getThemeValue()
            viewModel.getQrcodeValue()
            viewModel.getAlarmValue()
        },
        onThemeSelect = { selectedTheme ->
            viewModel.initSetTheme()
            viewModel.setTheme(selectedTheme)
        },
        onUpdateTheme = { onThemeSelect() },
        onUpdateQrcode = { qrcodeData = it },
        onUpdateAlarm = { alarmData = it },
        logoutUiState = logoutUiState,
        setThemeUiState = setThemeUiState,
        getProfileUiState = getProfileUiState,
        themeState = themeState,
        qrcodeState = qrcodeState,
        alarmState = alarmState,
        profileImageUiState = profileImageUiState,
        onErrorToast = onErrorToast,
        onEmailCheck = onEmailCheck
    )
}

@Composable
fun SettingScreen(
    role: String,
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    getProfile: () -> Unit,
    getSettingInfo: () -> Unit,
    onThemeSelect: (String) -> Unit,
    onUpdateTheme: () -> Unit,
    onUpdateQrcode: (String) -> Unit,
    onUpdateAlarm: (String) -> Unit,
    logoutUiState: LogoutUiState,
    setThemeUiState: SetThemeUiState,
    getProfileUiState: GetProfileUiState,
    themeState: String,
    qrcodeState: String,
    alarmState: String,
    profileImageUiState: ProfileImageUiState,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    onEmailCheck: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

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
            onErrorToast(logoutUiState.exception, "로그아웃에 실패 했습니다")
        }
    }

    when (setThemeUiState) {
        is SetThemeUiState.Loading -> Unit
        is SetThemeUiState.Success -> onUpdateTheme()
        is SetThemeUiState.Error -> Unit
    }

    when (profileImageUiState) {
        is ProfileImageUiState.Loading -> {
            isLoading = false
        }

        is ProfileImageUiState.Success -> {
            isLoading = true
            getProfile()
        }

        is ProfileImageUiState.Error -> {
            onErrorToast(profileImageUiState.exception, "네트워크 상태를 확인해 주세요")
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
        GomsRoleBackButton(role = if(role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT) {
            onBackClick()
        }
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SettingProfileCard(
                modifier = Modifier,
                onProfileClick = onProfileClick,
                getProfileUiState = getProfileUiState
            )
            Spacer(modifier = Modifier.height(32.dp))
            PasswordChangeButton(modifier = Modifier) {
                onEmailCheck()
            }
            Spacer(modifier = Modifier.height(24.dp))
            SelectThemeDropDown(
                modifier = Modifier,
                onThemeSelect = {
                    val selectedTheme = when (it) {
                        0 -> "Dark"
                        1 -> "Light"
                        2 -> "System"
                        else -> "Dark"
                    }
                    onThemeSelect(selectedTheme)
                },
                themeState = themeState
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (role == Authority.ROLE_STUDENT.name) {
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    title = "외출제 푸시 알림",
                    detail = "외출할 시간이 될 때마다 알려드려요",
                    switchOnBackground = colors.P5,
                    switchOffBackground = colors.G4,
                    isSwitchOn = alarmState == "On",
                    onFunctionOff = { if (alarmState == "On") onUpdateAlarm("Off") },
                    onFunctionOn = { if (alarmState == "Off") onUpdateAlarm("On") }
                )
                Spacer(modifier = Modifier.height(32.dp))
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    title = "카메라 바로 켜기",
                    detail = "앱을 실행하면 즉시 카메라가 켜져요",
                    switchOnBackground = colors.P5,
                    switchOffBackground = colors.G4,
                    isSwitchOn = qrcodeState == "On",
                    onFunctionOff = { if (qrcodeState == "On") onUpdateQrcode("Off") },
                    onFunctionOn = { if (qrcodeState == "Off") onUpdateQrcode("On") }
                )
            }
            if (role == Authority.ROLE_STUDENT_COUNCIL.name) {
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    title = "Qr 생성 바로 켜기",
                    detail = "앱을 실행하면 즉시 Qr코드를 생성해요",
                    switchOnBackground = colors.A7,
                    switchOffBackground = colors.G4,
                    isSwitchOn = qrcodeState == "On",
                    onFunctionOff = { if (qrcodeState == "On") onUpdateQrcode("Off") },
                    onFunctionOn = { if (qrcodeState == "Off") onUpdateQrcode("On") }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = "로그아웃",
                state = ButtonState.Logout
            ) {
                openDialog = true
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
    if (isLoading) {
        GomsCircularProgressIndicator()
    }
    if (openDialog) {
        GomsTwoButtonDialog(
            openDialog = openDialog,
            onStateChange = {
                openDialog = it
            },
            title = "로그아웃",
            content = "로그아웃 하시겠습니까?",
            dismissText = "취소",
            checkText = "로그아웃",
            onDismissClick = { openDialog = false }
        ) {
            onLogoutClick()
        }
    }
}