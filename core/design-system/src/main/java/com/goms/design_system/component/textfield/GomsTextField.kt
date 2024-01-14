package com.goms.design_system.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    onValueChange(it)
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
                        style = typography.caption
                    )
                }
            }
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
    }
}