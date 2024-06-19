package com.goms.design_system.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.R
import com.goms.design_system.component.timer.CountdownTimer
import com.goms.design_system.icon.InvisibleIcon
import com.goms.design_system.icon.SearchIcon
import com.goms.design_system.icon.VisibleIcon
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.util.gomsPreview
import kotlinx.coroutines.delay

@Composable
fun GomsTextField(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isEmail: Boolean = true,
    placeHolder: String = "",
    readOnly: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = FocusRequester(),
    setText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    maxLength: Int = if (isEmail) 6 else Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = {},
) {
    val isFocused = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            focusManager.clearFocus()
        }
    }

    Column {
        OutlinedTextField(
            value = setText,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            placeholder = {
                Text(
                    text = placeHolder,
                    style = typography.textMedium,
                    fontWeight = FontWeight.Normal,
                    color = if (isError) colors.N5 else colors.G7
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
                .focusRequester(focusRequester)
                .clip(RoundedCornerShape(12.dp))
                .background(colors.G1)
                .border(
                    width = 1.dp,
                    color = if (isError) colors.N5 else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                )
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            maxLines = maxLines,
            singleLine = singleLine,
            textStyle = typography.textMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = if (isError) colors.N5 else colors.WHITE,
                unfocusedTextColor = if (isError) colors.N5 else colors.WHITE,
                focusedPlaceholderColor = if (isError) colors.N5 else colors.G7,
                unfocusedPlaceholderColor = if (isError) colors.N5 else colors.G7,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = colors.I5
            ),
            trailingIcon = {
                if (isEmail) {
                    Text(
                        modifier = Modifier.padding(end = 16.dp),
                        text = "@gsm.hs.kr",
                        style = typography.textMedium,
                        fontWeight = FontWeight.Normal,
                        color = if (isError) colors.N5 else colors.G4
                    )
                }
            },
            readOnly = readOnly,
            visualTransformation = visualTransformation
        )
    }
}

@Composable
fun NumberTextField(
    modifier: Modifier = Modifier,
    setText: String,
    placeHolder: String = "",
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = FocusRequester(),
    isError: Boolean,
    errorText: String = "",
    maxLength: Int = 4,
    onValueChange: (String) -> Unit,
    onResendClick: () -> Unit,
) {
    val context = LocalContext.current
    val isFocused = remember { mutableStateOf(false) }
    val isErrorTextField = remember { mutableStateOf(isError) }
    val errorTextTextField = remember { mutableStateOf(errorText) }

    LaunchedEffect(isError, errorText) {
        isErrorTextField.value = isError
        errorTextTextField.value = errorText
    }

    DisposableEffect(Unit) {
        onDispose {
            focusManager.clearFocus()
        }
    }

    Column {
        OutlinedTextField(
            value = setText,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(
                    text = placeHolder,
                    style = typography.textMedium,
                    fontWeight = FontWeight.Normal,
                    color = if (isError) colors.N5 else colors.G7
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
                .focusRequester(focusRequester)
                .clip(RoundedCornerShape(12.dp))
                .background(colors.G1)
                .border(
                    width = 1.dp,
                    color = if (isError) colors.N5 else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                )
                .onFocusChanged {
                    isFocused.value = it.isFocused
                    if (it.isFocused) {
                        onValueChange("")
                    }
                },
            maxLines = 1,
            singleLine = true,
            textStyle = typography.textMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = if (isError) colors.N5 else colors.WHITE,
                unfocusedTextColor = if (isError) colors.N5 else colors.WHITE,
                focusedPlaceholderColor = if (isError) colors.N5 else colors.G7,
                unfocusedPlaceholderColor = if (isError) colors.N5 else colors.G7,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = colors.I5
            )
        )
    }
    CountdownTimer(
        isError = isErrorTextField.value,
        errorText = errorTextTextField.value,
        onTimerFinish = {
            isErrorTextField.value = true
            errorTextTextField.value = context.getString(R.string.time_expired)
        },
        onTimerReset = {
            isErrorTextField.value = false
            errorTextTextField.value = ""
            onResendClick()
        }
    )
}

@Composable
fun GomsPasswordTextField(
    modifier: Modifier = Modifier,
    isDescription: Boolean = false,
    isError: Boolean = false,
    placeHolder: String = "",
    readOnly: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = FocusRequester(),
    setText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    onValueChange: (String) -> Unit = {},
) {
    val isFocused = remember { mutableStateOf(false) }
    var passwordVisibility by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            focusManager.clearFocus()
        }
    }

    Column {
        OutlinedTextField(
            value = setText,
            onValueChange = {
                onValueChange(it)
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            placeholder = {
                Text(
                    text = placeHolder,
                    style = typography.textMedium,
                    fontWeight = FontWeight.Normal,
                    color = if (isError) colors.N5 else colors.G7
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
                .focusRequester(focusRequester)
                .clip(RoundedCornerShape(12.dp))
                .background(colors.G1)
                .border(
                    width = 1.dp,
                    color = if (isError) colors.N5 else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                )
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            maxLines = maxLines,
            singleLine = singleLine,
            textStyle = typography.textMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = if (isError) colors.N5 else colors.WHITE,
                unfocusedTextColor = if (isError) colors.N5 else colors.WHITE,
                focusedPlaceholderColor = if (isError) colors.N5 else colors.G7,
                unfocusedPlaceholderColor = if (isError) colors.N5 else colors.G7,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = colors.I5
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    if (passwordVisibility) VisibleIcon(tint = colors.G7) else InvisibleIcon(tint = colors.G7)
                }
            },
            readOnly = readOnly,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
        )
        Column(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (isDescription) {
                Text(
                    text = stringResource(id = R.string.password_conditions),
                    color = colors.G4,
                    style = typography.buttonLarge
                )
            }
        }
    }
}

@Composable
fun GomsSearchTextField(
    modifier: Modifier = Modifier,
    debounceTime: Long = 300L,
    placeHolder: String = "",
    readOnly: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = FocusRequester(),
    setText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = {},
    onSearchTextChange: (String) -> Unit = {}
) {
    val isFocused = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            focusManager.clearFocus()
        }
    }

    LaunchedEffect(setText) {
        delay(debounceTime)
        onSearchTextChange(setText)
    }

    Column {
        TextField(
            value = setText,
            onValueChange = {
                onValueChange(it)
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            placeholder = {
                Text(
                    text = placeHolder,
                    style = typography.textMedium,
                    fontWeight = FontWeight.Normal,
                    color = colors.G7
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .clip(RoundedCornerShape(12.dp))
                .background(colors.G1)
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            maxLines = maxLines,
            singleLine = singleLine,
            textStyle = typography.textMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.WHITE,
                unfocusedTextColor = colors.WHITE,
                focusedPlaceholderColor = colors.G7,
                unfocusedPlaceholderColor = colors.G7,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = colors.I5
            ),
            trailingIcon = {
                SearchIcon(tint = colors.G4)
            },
            readOnly = readOnly,
            visualTransformation = visualTransformation
        )
    }
}

@Preview
@Composable
fun GomsTextFieldPreview() {
    gomsPreview {
        Column {
            GomsTextField(
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Test",
                isError = false,
                onValueChange = {},
                setText = ""
            )
            GomsTextField(
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Test",
                isError = true,
                onValueChange = {},
                setText = ""
            )
            NumberTextField(
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Test",
                isError = false,
                onValueChange = {},
                setText = ""
            ) {}
            NumberTextField(
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Test",
                isError = true,
                errorText = "Error",
                onValueChange = {},
                setText = ""
            ) {}
            GomsPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                isDescription = true,
                placeHolder = "Test",
                isError = false,
                onValueChange = {},
                setText = ""
            )
            GomsSearchTextField(
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Test",
                onValueChange = {},
                setText = ""
            ) {}
        }
    }
}
