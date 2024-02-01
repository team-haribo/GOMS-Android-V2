package com.goms.main

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.textfield.GomsSearchTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.main.component.OutingStatusList
import com.goms.main.component.OutingStatusText
import com.goms.main.viewmodel.GetOutingCountUiState
import com.goms.main.viewmodel.GetOutingListUiState
import com.goms.main.viewmodel.MainViewModelProvider
import com.goms.model.enum.Authority

@Composable
fun OutingStatusRoute(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit
) {
    MainViewModelProvider(viewModelStoreOwner = viewModelStoreOwner) { viewModel ->
        val role by viewModel.role.collectAsStateWithLifecycle(initialValue = "")
        val outingSearch by viewModel.outingSearch.collectAsStateWithLifecycle()
        val getOutingListUiState by viewModel.getOutingListUiState.collectAsStateWithLifecycle()
        val getOutingCountUiState by viewModel.getOutingCountUiState.collectAsStateWithLifecycle()

        OutingStatusScreen(
            role = if (role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT,
            outingSearch = outingSearch,
            onOutingSearchChange = viewModel::onOutingSearchChange,
            getOutingListUiState = getOutingListUiState,
            getOutingCountUiState = getOutingCountUiState,
            onBackClick = onBackClick
        )
    }
}

@Composable
fun OutingStatusScreen(
    role: Authority,
    outingSearch: String,
    onOutingSearchChange: (String) -> Unit,
    getOutingListUiState: GetOutingListUiState,
    getOutingCountUiState: GetOutingCountUiState,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.BLACK)
                .statusBarsPadding()
                .navigationBarsPadding()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            GomsBackButton {
                onBackClick()
            }
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutingStatusText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(16.dp))
                GomsSearchTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeHolder = "학생 검색...",
                    setText = outingSearch,
                    onValueChange = onOutingSearchChange,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutingStatusList(
                    role = role,
                    getOutingListUiState = getOutingListUiState,
                    getOutingCountUiState = getOutingCountUiState
                ) {}
            }
        }
    }
}