package com.goms.main

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.icon.SettingIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.main.viewmodel.GetLateRankListUiState
import com.goms.main.viewmodel.GetOutingCountUiState
import com.goms.main.viewmodel.GetOutingListUiState
import com.goms.main.viewmodel.GetProfileUiState
import com.goms.model.enum.Authority
import com.goms.ui.GomsTopBar
import com.goms.ui.GomsFloatingButton
import com.goms.main.component.MainLateCard
import com.goms.main.component.MainOutingCard
import com.goms.main.component.MainProfileCard
import com.goms.main.viewmodel.MainViewModelProvider
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainRoute(
    viewModelStoreOwner: ViewModelStoreOwner,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onStudentManagementClick: () -> Unit,
    onQrcodeClick: (role: Authority) -> Unit,
    onSettingClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit
) {
    MainViewModelProvider(viewModelStoreOwner = viewModelStoreOwner) { viewModel ->
        val role by viewModel.role.collectAsStateWithLifecycle(initialValue = "")
        val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
        val getProfileUiState by viewModel.getProfileUiState.collectAsStateWithLifecycle()
        val getLateRankListUiState by viewModel.getLateRankListUiState.collectAsStateWithLifecycle()
        val getOutingListUiState by viewModel.getOutingListUiState.collectAsStateWithLifecycle()
        val getOutingCountUiState by viewModel.getOutingCountUiState.collectAsStateWithLifecycle()

        MainScreen(
            role = if (role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT,
            isRefreshing = isRefreshing,
            getProfileUiState = getProfileUiState,
            getLateRankListUiState = getLateRankListUiState,
            getOutingListUiState = getOutingListUiState,
            getOutingCountUiState = getOutingCountUiState,
            onOutingStatusClick = onOutingStatusClick,
            onLateListClick = onLateListClick,
            onStudentManagementClick = onStudentManagementClick,
            onQrcodeClick = onQrcodeClick,
            onSettingClick = onSettingClick,
            onErrorToast = onErrorToast,
            mainCallBack = {
                viewModel.getProfile()
                viewModel.getLateRankList()
                viewModel.getOutingCount()
            }
        )
    }
}

@Composable
fun MainScreen(
    role: Authority,
    isRefreshing: Boolean,
    getProfileUiState: GetProfileUiState,
    getLateRankListUiState: GetLateRankListUiState,
    getOutingListUiState: GetOutingListUiState,
    getOutingCountUiState: GetOutingCountUiState,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onStudentManagementClick: () -> Unit,
    onQrcodeClick: (role: Authority) -> Unit,
    onSettingClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    mainCallBack: () -> Unit
) {
    var isPermissionRequest by rememberSaveable { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { isGrantedMap: Map<String, Boolean> -> }

    if (!isPermissionRequest) {
        LaunchedEffect(true) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        Manifest.permission.READ_MEDIA_IMAGES
                    } else {
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    Manifest.permission.POST_NOTIFICATIONS
                )
            )
            isPermissionRequest = true
        }
    }

    LaunchedEffect(true) {
        mainCallBack()
    }

    val scrollState = rememberScrollState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    GomsTheme { colors, typography ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { mainCallBack() },
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    backgroundColor = colors.G2,
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
                        role = role,
                        icon = { SettingIcon(tint = colors.G7) },
                        onSettingClick = onSettingClick,
                        onAdminClick = { onStudentManagementClick() }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        MainProfileCard(
                            getProfileUiState = getProfileUiState,
                            onErrorToast = onErrorToast
                        )
                        MainLateCard(
                            role = role,
                            getLateRankListUiState = getLateRankListUiState,
                            onErrorToast = onErrorToast
                        ) {
                            onLateListClick()
                        }
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
}
