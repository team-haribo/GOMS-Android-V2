package com.goms.setting

import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.theme.GomsTheme
import com.goms.model.enum.Authority
import com.goms.setting.component.SettingProfileCard
import com.goms.setting.component.PasswordChangeButton
import com.goms.setting.component.SelectThemeDropDown
import com.goms.setting.component.SettingSwitchComponent
import com.goms.setting.viewmodel.GetProfileUiState
import com.goms.setting.viewmodel.SettingViewModelProvider

@Composable
fun SettingRoute(
    onBackClick: () -> Unit,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    SettingViewModelProvider(viewModelStoreOwner = viewModelStoreOwner) { viewModel ->
        val getProfileUiState by viewModel.getProfileUiState.collectAsStateWithLifecycle()
        val role by viewModel.role.collectAsStateWithLifecycle(initialValue = "")

        SettingScreen(
            onBackClick = onBackClick,
            getProfile = {
                viewModel.initGetProfile()
                viewModel.getProfile()
            },
            role = role,
            getProfileUiState = getProfileUiState
        )
    }
}
@Composable
fun SettingScreen(
    onBackClick: () -> Unit,
    getProfile: () -> Unit,
    role: String,
    getProfileUiState: GetProfileUiState
) {
    LaunchedEffect("mypage init") { getProfile() }

    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.BLACK)
                .statusBarsPadding(),
        ) {
            GomsBackButton { onBackClick() }
            Spacer(modifier = Modifier.height(16.dp))
            SettingProfileCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                getProfileUiState = getProfileUiState
            )
            Spacer(modifier = Modifier.height(32.dp))
            PasswordChangeButton(modifier = Modifier.padding(horizontal = 20.dp)) {}
            Spacer(modifier = Modifier.height(24.dp))
            SelectThemeDropDown(modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(modifier = Modifier.height(24.dp))
            if(role == Authority.ROLE_STUDENT.name) {
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 28.dp),
                    title = "외출제 푸시 알림",
                    detail = "외출할 시간이 될 때마다 알려드려요",
                    switchOnBackground = colors.P5,
                    switchOffBackground = colors.G4,
                    onFunctionOff = { Log.d("testt","off1") },
                    onFunctionOn = { Log.d("testt","on1") }
                )
                Spacer(modifier = Modifier.height(32.dp))
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 28.dp),
                    title = "카메라 바로 켜기",
                    detail = "앱을 실행하면 즉시 카메라가 켜져요",
                    switchOnBackground = colors.P5,
                    switchOffBackground = colors.G4,
                    onFunctionOff = {  },
                    onFunctionOn = { }
                )
            } else {
                SettingSwitchComponent(
                    modifier = Modifier.padding(horizontal = 28.dp),
                    title = "Qr 생성 바로 켜기",
                    detail = "앱을 실행하면 즉시 Qr코드를 생성해요",
                    switchOnBackground = colors.A7,
                    switchOffBackground = colors.G4,
                    onFunctionOff = { },
                    onFunctionOn = { }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                .navigationBarsPadding(),
            contentAlignment = Alignment.BottomCenter
        ) {
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = "로그아웃",
                state = ButtonState.Logout,
                onClick = {}
            )
        }
    }
}