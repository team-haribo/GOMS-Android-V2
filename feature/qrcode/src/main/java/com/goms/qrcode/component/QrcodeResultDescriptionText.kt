package com.goms.qrcode.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

@Composable
internal fun QrcodeResultDescriptionText(text: String) {
    val parts = text.split(Keyword, ignoreCase = true)
    val coloredText = buildAnnotatedString {
        parts.forEachIndexed { index, part ->
            withStyle(style = SpanStyle(color = colors.G7)) {
                append(part)
            }
            if (index < parts.size - 1) {
                withStyle(style = SpanStyle(color = colors.P5)) {
                    append(Keyword)
                }
            }
        }
    }

    Text(
        text = coloredText,
        style = typography.textMedium,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Normal
    )
}

private const val Keyword = "7시 25분"