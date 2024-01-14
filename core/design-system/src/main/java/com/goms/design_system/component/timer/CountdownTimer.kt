package com.goms.design_system.component.timer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.goms.design_system.theme.GomsTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun CountdownTimer(onTimerFinish: () -> Unit) {
    var remainingTime by remember { mutableIntStateOf(5 * 60) }

    LaunchedEffect(remainingTime) {
        while (remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
        onTimerFinish()
    }

    GomsTheme { colors, typography ->
        Text(
            text = formatTime(remainingTime.toLong()),
            color = colors.G4,
            style = typography.buttonLarge
        )
    }
}

@Composable
fun formatTime(seconds: Long): String {
    val minutes = TimeUnit.SECONDS.toMinutes(seconds)
    val remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%02d:%02d", minutes, remainingSeconds)
}