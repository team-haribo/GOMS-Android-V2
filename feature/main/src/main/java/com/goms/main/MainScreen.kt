package com.goms.main

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.icon.SettingIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.main.viewmodel.GetLateRankListUiState
import com.goms.main.viewmodel.GetOutingCountUiState
import com.goms.main.viewmodel.GetOutingListUiState
import com.goms.main.viewmodel.GetProfileUiState
import com.goms.main.viewmodel.MainViewModel
import com.goms.model.enum.Authority
import com.goms.ui.GomsTopBar
import com.goms.ui.GomsFloatingButton
import com.goms.main.component.MainLateCard
import com.goms.main.component.MainOutingCard
import com.goms.main.component.MainProfileCard

@Composable
fun MainRoute(
    viewModel: MainViewModel = hiltViewModel()
) {
    val role by viewModel.role.collectAsStateWithLifecycle(initialValue = "")
    val getProfileUiState by viewModel.getProfileUiState.collectAsStateWithLifecycle()
    val getLateRankListUiState by viewModel.getLateRankListUiState.collectAsStateWithLifecycle()
    val getOutingListUiState by viewModel.getOutingListUiState.collectAsStateWithLifecycle()
    val getOutingCountUiState by viewModel.getOutingCountUiState.collectAsStateWithLifecycle()

    MainScreen(
        role = if (role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT,
        getProfileUiState = getProfileUiState,
        getLateRankListUiState = getLateRankListUiState,
        getOutingListUiState = getOutingListUiState,
        getOutingCountUiState = getOutingCountUiState,
        getData = {
            viewModel.getProfile()
            viewModel.getLateRankList()
            viewModel.getOutingCount()
        }
    )
}

@Composable
fun MainScreen(
    role: Authority,
    getProfileUiState: GetProfileUiState,
    getLateRankListUiState: GetLateRankListUiState,
    getOutingListUiState: GetOutingListUiState,
    getOutingCountUiState: GetOutingCountUiState,
    getData: () -> Unit
) {
    LaunchedEffect(true) {
        getData()
    }

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
                    role = role,
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
                    MainProfileCard(
                        role = role,
                        getProfileUiState = getProfileUiState
                    )
                    MainLateCard(
                        role = role,
                        getLateRankListUiState = getLateRankListUiState
                    ) {}
                    MainOutingCard(
                        role = role,
                        getOutingListUiState = getOutingListUiState,
                        getOutingCountUiState = getOutingCountUiState
                    ) {}
                }
            }
            GomsFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp),
                role = role
            ) {}
        }
    }
}