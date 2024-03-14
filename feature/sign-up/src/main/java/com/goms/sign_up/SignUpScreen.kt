package com.goms.sign_up

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.textfield.GomsTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.request.auth.SendNumberRequest
import com.goms.sign_up.component.SelectGenderDropDown
import com.goms.sign_up.component.SelectMajorDropDown
import com.goms.sign_up.component.SignUpText
import com.goms.sign_up.viewmodel.SendNumberUiState
import com.goms.sign_up.viewmodel.SignUpViewModel
import com.goms.ui.isStrongEmail

@Composable
fun SignUpRoute(
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val sendNumberUiState by viewModel.sendNumberUiState.collectAsState()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val gender by viewModel.gender.collectAsStateWithLifecycle()
    val major by viewModel.major.collectAsStateWithLifecycle()

    SignUpScreen(
        name = name,
        email = email,
        gender = gender,
        major = major,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onGenderChange = viewModel::onGenderChange,
        onMajorChange = viewModel::onMajorChange,
        sendNumberUiState = sendNumberUiState,
        onBackClick = onBackClick,
        onNumberClick = onNumberClick,
        onErrorToast = onErrorToast,
        signUpCallBack = { viewModel.sendNumber(body = SendNumberRequest("${viewModel.email.value}@gsm.hs.kr")) },
        initCallBack = { viewModel.initSendNumber() }
    )
}


@Composable
fun SignUpScreen(
    name: String,
    email: String,
    gender: String,
    major: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onMajorChange: (String) -> Unit,
    sendNumberUiState: SendNumberUiState,
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    signUpCallBack: () -> Unit,
    initCallBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    DisposableEffect(sendNumberUiState) {
        when (sendNumberUiState) {
            is SendNumberUiState.Loading -> Unit
            is SendNumberUiState.Success -> onNumberClick()
            is SendNumberUiState.Error -> {
                isLoading = false
                onErrorToast(sendNumberUiState.exception, "인증번호 전송이 실패했습니다.")
            }
        }
        onDispose { initCallBack() }
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.BACKGROUND)
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
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SignUpText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.weight(1.1f))
                GomsTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeHolder = "이름",
                    setText = name,
                    onValueChange = onNameChange,
                    isEmail = false,
                    singleLine = true
                )
                GomsTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeHolder = "이메일",
                    setText = email,
                    onValueChange = onEmailChange,
                    singleLine = true
                )
                SelectGenderDropDown(
                    modifier = Modifier,
                    gender = gender,
                    onSelectGender = onGenderChange
                ) {
                    focusManager.clearFocus()
                }
                Spacer(modifier = Modifier.height(30.dp))
                SelectMajorDropDown(
                    modifier = Modifier,
                    major = major,
                    onSelectMajor = onMajorChange
                ) {
                    focusManager.clearFocus()
                }
                Spacer(modifier = Modifier.weight(1f))
                GomsButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "인증번호 받기",
                    state = if (name.isNotBlank() && email.isNotBlank() && gender.isNotBlank() && major.isNotBlank()) ButtonState.Normal
                    else ButtonState.Enable
                ) {
                    if (!isStrongEmail(email)) {
                        isLoading = false
                        onErrorToast(null, "이메일 형식이 올바르지 않습니다")
                    } else {
                        signUpCallBack()
                        isLoading = true
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        if (isLoading) {
            GomsCircularProgressIndicator()
        }
    }
}