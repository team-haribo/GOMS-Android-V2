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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.component.textfield.GomsTextField
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.request.auth.SendNumberRequestModel
import com.goms.model.util.ResourceKeys
import com.goms.sign_up.component.SelectGenderDropDown
import com.goms.sign_up.component.SelectMajorDropDown
import com.goms.sign_up.component.SignUpText
import com.goms.sign_up.viewmodel.uistate.SendNumberUiState
import com.goms.sign_up.viewmodel.SignUpViewModel

@Composable
internal fun SignUpRoute(
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val sendNumberUiState by viewModel.sendNumberUiState.collectAsStateWithLifecycle()
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
        signUpCallBack = { viewModel.sendNumber(body = SendNumberRequestModel("${viewModel.email.value}${ResourceKeys.EMAIL_DOMAIN}")) },
        initCallBack = { viewModel.initSendNumber() }
    )
}


@Composable
private fun SignUpScreen(
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
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
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
            is SendNumberUiState.EmailNotValid -> {
                isLoading = false
                onErrorToast(null, R.string.error_email_not_valid)
            }

            is SendNumberUiState.Error -> {
                isLoading = false
                onErrorToast(sendNumberUiState.exception, R.string.error_send_number)
            }
        }
        onDispose { initCallBack() }
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
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
            GomsSpacer(size = SpacerSize.MediumLarge)
            GomsTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeHolder = stringResource(id = R.string.name),
                setText = name,
                onValueChange = onNameChange,
                isEmail = false,
                singleLine = true
            )
            GomsSpacer(size = SpacerSize.Medium)
            GomsTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeHolder = stringResource(id = R.string.email),
                setText = email,
                onValueChange = onEmailChange,
                singleLine = true
            )
            GomsSpacer(size = SpacerSize.Medium)
            SelectGenderDropDown(
                onSelectGender = onGenderChange
            ) {
                focusManager.clearFocus()
            }
            GomsSpacer(size = SpacerSize.Medium)
            SelectMajorDropDown(
                onSelectMajor = onMajorChange
            ) {
                focusManager.clearFocus()
            }
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.get_number),
                state = if (name.isNotBlank() && email.isNotBlank() && gender.isNotBlank() && major.isNotBlank()) ButtonState.Normal
                else ButtonState.Enable
            ) {
                signUpCallBack()
                isLoading = true
            }
            GomsSpacer(height = 100.dp)
        }
    }
    if (isLoading) {
        GomsCircularProgressIndicator()
    }
}