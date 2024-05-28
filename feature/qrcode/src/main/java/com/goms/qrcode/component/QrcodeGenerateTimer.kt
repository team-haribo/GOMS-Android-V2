package com.goms.qrcode.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.qrcode.R
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
internal fun QrcodeGenerateTimer(
    onTimerFinish: () -> Unit,
) {
    var remainingTime by remember { mutableIntStateOf(5 * 60) }

    LaunchedEffect(remainingTime) {
        if (remainingTime == 0) {
            onTimerFinish()
        }
        delay(1000)
        remainingTime--
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.until_qrcode_expires),
            color = colors.G7,
            style = typography.textSmall,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = formatTime(remainingTime.toLong()),
            color = colors.A7,
            style = typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun formatTime(seconds: Long): String {
    val minutes = TimeUnit.SECONDS.toMinutes(seconds)
    val remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%01d분 %02d초", minutes, remainingSeconds)
}