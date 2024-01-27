package com.goms.main

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goms.design_system.icon.SettingIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.model.enum.Authority
import com.goms.ui.GomsTopBar
import com.goms.ui.GomsFloatingButton
import com.goms.ui.MainLateCard
import com.goms.ui.MainOutingCard
import com.goms.ui.MainProfileCard

@Composable
fun MainRoute() {
    MainScreen()
}

@Composable
fun MainScreen() {
    val scrollState = rememberScrollState()

    GomsTheme { colors, typography ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .background(colors.BLACK)
        ) {
            Column {
                GomsTopBar(
                    role = Authority.ROLE_STUDENT,
                    icon = { SettingIcon(tint = colors.G7) },
                    onSettingClick = {},
                    onAdminClick = {}
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    MainProfileCard(role = Authority.ROLE_STUDENT)
                    MainLateCard(role = Authority.ROLE_STUDENT) {}
                    MainOutingCard(role = Authority.ROLE_STUDENT) {}
                }
            }
            GomsFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp),
                role = Authority.ROLE_STUDENT
            ) {}
        }
    }
}