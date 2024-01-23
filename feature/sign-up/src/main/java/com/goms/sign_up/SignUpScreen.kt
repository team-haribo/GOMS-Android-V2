package com.goms.sign_up

import android.content.pm.ActivityInfo
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.common.result.Result
import com.goms.design_system.component.bottomsheet.SelectorBottomSheet
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.textfield.GomsBottomSheetTextField
import com.goms.design_system.component.textfield.GomsTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.goms.model.request.auth.SendNumberRequest
import com.goms.sign_up.component.SignUpText
import com.goms.sign_up.viewmodel.SignUpViewModelProvider

@Composable
fun SignUpRoute(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
) {
    SignUpViewModelProvider(viewModelStoreOwner = viewModelStoreOwner) { viewModel ->
        val sendNumberResponse = viewModel.sendNumberResponse.collectAsStateWithLifecycle()
        val name by viewModel.name.collectAsStateWithLifecycle()
        val email by viewModel.email.collectAsStateWithLifecycle()
        val gender by viewModel.gender.collectAsStateWithLifecycle()
        val major by viewModel.major.collectAsStateWithLifecycle()

        when (sendNumberResponse.value) {
            is Result.Loading -> Unit
            is Result.Success -> {
                onNumberClick()
                viewModel.initSendNumber()
            }
            is Result.Error -> {}
        }

        SignUpScreen(
            name = name,
            email = email,
            gender = gender,
            major = major,
            onNameChange = viewModel::onNameChange,
            onEmailChange = viewModel::onEmailChange,
            onGenderChange = viewModel::onGenderChange,
            onMajorChange =  viewModel::onMajorChange,
            onBackClick = onBackClick,
            signUpCallBack = { viewModel.sendNumber(body = SendNumberRequest("${viewModel.email.value}@gsm.hs.kr")) }
        )
    }
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
    onBackClick: () -> Unit,
    signUpCallBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()

    var onGenderBottomSheetOpenClick by rememberSaveable { mutableStateOf(false) }
    var onMajorBottomSheetOpenClick by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
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
                GomsBottomSheetTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeHolder = "성별",
                    setText = gender,
                    onValueChange = onGenderChange,
                    readOnly = true,
                    singleLine = true
                ) {
                    onGenderBottomSheetOpenClick = true
                    focusManager.clearFocus()
                }
                GomsBottomSheetTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeHolder = "과",
                    setText = major,
                    onValueChange = onMajorChange,
                    readOnly = true,
                    singleLine = true
                ) {
                    onMajorBottomSheetOpenClick = true
                    focusManager.clearFocus()
                }
                Spacer(modifier = Modifier.weight(1f))
                GomsButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "인증번호 받기",
                    state = if (name.isNotBlank() && email.isNotBlank() && gender.isNotBlank() && major.isNotBlank()) ButtonState.Normal
                    else ButtonState.Enable
                ) {
                    signUpCallBack()
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        if (onGenderBottomSheetOpenClick) {
            SelectorBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                title = "성별",
                list = listOf(Gender.MAN.value, Gender.WOMAN.value),
                selected = gender,
                itemChange = onGenderChange,
                closeSheet = {
                    onGenderBottomSheetOpenClick = false
                }
            )
        }
        if (onMajorBottomSheetOpenClick) {
            SelectorBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                title = "과",
                list = listOf(Major.SW_DEVELOP.value, Major.SMART_IOT.value, Major.AI.value),
                selected = major,
                itemChange = onMajorChange,
                closeSheet = {
                    onMajorBottomSheetOpenClick = false
                }
            )
        }
    }
}