package com.goms.design_system.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.goms.design_system.R

object GomsTypography {
    private val pretendard = FontFamily(
        Font(R.font.pretendard_bold, FontWeight.Bold),
        Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
        Font(R.font.pretendard_extralight, FontWeight.ExtraLight),
        Font(R.font.pretendard_light, FontWeight.Light),
        Font(R.font.pretendard_medium, FontWeight.Medium),
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_semibold, FontWeight.SemiBold),
        Font(R.font.pretendard_thin, FontWeight.Thin),
    )

    @Stable
    val titleLarge = TextStyle(
        fontFamily = pretendard,
        fontSize = 29.sp,
        lineHeight = 48.sp
    )

    @Stable
    val titleMedium = TextStyle(
        fontFamily = pretendard,
        fontSize = 24.sp,
        lineHeight = 40.sp
    )

    @Stable
    val titleSmall = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        lineHeight = 32.sp
    )

    @Stable
    val textLarge = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        lineHeight = 32.sp
    )

    @Stable
    val textMedium = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        lineHeight = 28.sp
    )

    @Stable
    val textSmall = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        lineHeight = 24.sp
    )

    @Stable
    val caption = TextStyle(
        fontFamily = pretendard,
        fontSize = 12.sp,
        lineHeight = 20.sp
    )

    @Stable
    val buttonLarge = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        lineHeight = 48.sp
    )

    @Stable
    val buttonMedium = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        lineHeight = 40.sp
    )

    @Stable
    val buttonSmall = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        lineHeight = 32.sp
    )
}