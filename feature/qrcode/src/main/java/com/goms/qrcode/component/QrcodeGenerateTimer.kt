package com.goms.qrcode.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun QrcodeGenerateTimer(
    onTimerFinish: () -> Unit,
) {
    var remainingTime by remember { mutableIntStateOf(5 * 60) }

    LaunchedEffect(remainingTime) {
        while (remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
        if (remainingTime == 0) {
            onTimerFinish()
            remainingTime--
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "QR코드 만료까지",
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
fun formatTime(seconds: Long): String {
    val minutes = TimeUnit.SECONDS.toMinutes(seconds)
    val remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%01d분 %02d초", minutes, remainingSeconds)
}