package com.goms.design_system.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.modifier.gomsClickable
import com.goms.design_system.theme.GomsTheme

@Composable
fun GomsTextField(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    placeHolder: String = "",
    readOnly: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = FocusRequester(),
    errorText: String = "",
    setText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = {},
) {
    val isFocused = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            focusManager.clearFocus()
        }
    }

    GomsTheme { colors, typography ->
        Column {
            OutlinedTextField(
                value = setText,
                onValueChange = {
                    if (it.length <= 4) {
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
                        color = if (isError) colors.N5 else colors.G4
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
                        color = if (isError) colors.N5 else colors.WHITE.copy(0.15f),
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
                    focusedPlaceholderColor = if (isError) colors.N5 else colors.G4,
                    unfocusedPlaceholderColor = if (isError) colors.N5 else colors.G4,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = colors.I5
                ),
                trailingIcon = {
                    Text(
                        modifier = Modifier.padding(end = 16.dp),
                        text = "@gsm.hs.kr",
                        style = typography.textMedium,
                        fontWeight = FontWeight.Normal,
                        color = if (isError) colors.N5 else colors.G4
                    )
                },
                readOnly = readOnly,
                visualTransformation = visualTransformation
            )
            Column(
                modifier = Modifier
                    .height(32.dp)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                if (isError) {
                    Text(
                        text = errorText,
                        color = colors.N5,
                        style = typography.buttonLarge
                    )
                }
            }
        }
    }
}

@Composable
fun NumberTextField(
    text: String,
    isError: Boolean,
    errorText: String = "",
    onValueChange: (String) -> Unit,
    onResendClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    GomsTheme { colors, typography ->
        Column {
            BasicTextField(
                value = text.take(4),
                onValueChange = {
                    onValueChange(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                decorationBox = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        text.forEachIndexed { index, char ->
                            NumberTextFieldCharContainer(
                                modifier = Modifier.weight(1f),
                                text = char,
                                isError = isError
                            )
                        }
                        repeat(4 - text.length) {
                            NumberTextFieldCharContainer(
                                modifier = Modifier.weight(1f),
                                text = ' ',
                                isError = isError
                            )
                        }
                    }
                },
                singleLine = true
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isError) {
                    Text(
                        text = errorText,
                        color = colors.N5,
                        style = typography.buttonLarge
                    )
                } else {
                    Column {
                        content()
                    }
                }
                Text(
                    modifier = Modifier.gomsClickable { onResendClick() },
                    text = "재발송",
                    color = colors.I5,
                    style = typography.buttonLarge
                )
            }
        }
    }
}

@Composable
private fun NumberTextFieldCharContainer(
    modifier: Modifier,
    text: Char,
    isError: Boolean,
) {
    val shape = remember { RoundedCornerShape(12.dp) }

    GomsTheme { colors, typography ->
        Box(
            modifier = modifier
                .size(
                    width = 74.dp,
                    height = 64.dp,
                )
                .background(
                    color = colors.G1,
                    shape = shape,
                )
                .border(
                    width = 1.dp,
                    color = if (isError) colors.N5 else colors.WHITE.copy(0.15f),
                    shape = shape
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text.toString(),
                color = colors.WHITE,
                style = typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GomsTextFieldPreview() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
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
            errorText = "오류",
            onValueChange = {},
            setText = ""
        )
        NumberTextField(
            text = "1234",
            isError = false,
            onValueChange = {},
            onResendClick = {},
            content = {}
        )
        NumberTextField(
            text = "1234",
            isError = true,
            errorText = "오류",
            onValueChange = {},
            onResendClick = {},
            content = {}
        )
    }
}