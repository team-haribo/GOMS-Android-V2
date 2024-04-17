package com.goms.find_password

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.dialog.GomsOneButtonDialog
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.textfield.GomsPasswordTextField
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.keyboardAsState
import com.goms.model.request.account.FindPasswordRequestModel
import com.goms.find_password.component.RePasswordText
import com.goms.find_password.viewmodel.uistate.FindPasswordUiState
import com.goms.find_password.viewmodel.FindPasswordViewmodel
import com.goms.model.util.ResourceKeys
import com.goms.ui.isValidPassword

@Composable
fun FindPasswordRoute(
    onBackClick: () -> Unit,
    onSuccessClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    viewModel: FindPasswordViewmodel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    val findPasswordUiState by viewModel.findPasswordUiState.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val passwordCheck by viewModel.passwordCheck.collectAsStateWithLifecycle()

    FindPasswordScreen(
        password = password,
        passwordCheck = passwordCheck,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordCheckChange = viewModel::onCheckPasswordChange,
        onSuccessClick = onSuccessClick,
        onBackClick = onBackClick,
        onErrorToast = onErrorToast,
        findPasswordUiState = findPasswordUiState,
        initFindPassword = { viewModel.initFindPassword() },
        findPasswordCallback = {
            viewModel.findPassword(
                body = FindPasswordRequestModel(
                    email = "${viewModel.email.value}${ResourceKeys.EMAIL_DOMAIN}",
                    password = viewModel.password.value
                )
            )
        }
    )
}

@Composable
fun FindPasswordScreen(
    password: String,
    passwordCheck: String,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
    onSuccessClick: () -> Unit,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    findPasswordUiState: FindPasswordUiState,
    initFindPassword: () -> Unit,
    findPasswordCallback: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    val animatedSpacerHeight by animateDpAsState(targetValue = if (!isKeyboardOpen) 100.dp else 16.dp)
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var openDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    DisposableEffect(findPasswordUiState) {
        when (findPasswordUiState) {
            is FindPasswordUiState.Loading -> Unit
            is FindPasswordUiState.Success -> {
                openDialog = true
                isLoading = false
            }

            is FindPasswordUiState.BadRequest -> {
                isLoading = false
                onErrorToast(null, "이미 사용중인 비밀번호입니다")
            }

            is FindPasswordUiState.Error -> {
                isLoading = false
                onErrorToast(findPasswordUiState.exception, "비밀번호 찾기가 실패했습니다")
            }
        }
        onDispose { initFindPassword() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.BACKGROUND)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
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
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RePasswordText(modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(28.dp))
            GomsPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                isDescription = false,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeHolder = "비밀번호",
                setText = password,
                onValueChange = onPasswordChange,
                singleLine = true
            )
            GomsPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                isDescription = true,
                isError = isError,
                errorText = errorText,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeHolder = "비밀번호 확인",
                setText = passwordCheck,
                onValueChange = onPasswordCheckChange,
                singleLine = true
            )
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = "완료",
                state = if (password.isNotBlank() && passwordCheck.isNotBlank()) ButtonState.Normal
                else ButtonState.Enable
            ) {
                when {
                    password != passwordCheck -> {
                        isError = true
                        errorText = "비밀번호가 일치하지 않습니다"
                        onErrorToast(null, "비밀번호가 일치하지 않습니다")
                    }

                    !isValidPassword(password) -> {
                        isError = true
                        errorText = "비밀번호 요구사항을 충족하지 않습니다"
                        onErrorToast(null, "비밀번호 요구사항을 충족하지 않습니다")
                    }

                    else -> {
                        findPasswordCallback()
                        isLoading = true
                    }
                }
            }
            Spacer(modifier = Modifier.height(animatedSpacerHeight))
        }
    }
    if (isLoading) {
        GomsCircularProgressIndicator()
    }
    if (openDialog) {
        GomsOneButtonDialog(
            openDialog = openDialog,
            onStateChange = {
                openDialog = it
            },
            title = "재설정 완료",
            content = "비밀번호를 재설정했어요.\n" +
                    "홈으로 돌아갈게요!",
            buttonText = "확인",
            onClick = onSuccessClick
        )
    }
}