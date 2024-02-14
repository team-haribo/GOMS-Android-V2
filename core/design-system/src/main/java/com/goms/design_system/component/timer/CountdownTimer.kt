package com.goms.design_system.component.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.theme.GomsTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun CountdownTimer(
    isError: Boolean,
    errorText: String,
    onTimerFinish: () -> Unit,
    onTimerReset: () -> Unit
) {
    var remainingTime by remember { mutableIntStateOf(5 * 60) }

    LaunchedEffect(remainingTime) {
        while (remainingTime > 0) {
            delay(1000)
            remainingTime--
        }

        onTimerFinish()
    }

    GomsTheme { colors, typography ->
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
                Text(
                    text = formatTime(remainingTime.toLong()),
                    color = colors.G4,
                    style = typography.buttonLarge
                )
            }
            Text(
                modifier = Modifier.gomsClickable {
                    remainingTime = 5 * 60
                    onTimerReset()
                },
                text = "재발송",
                color = colors.I5,
                style = typography.buttonLarge
            )
        }
    }
}

@Composable
fun formatTime(seconds: Long): String {
    val minutes = TimeUnit.SECONDS.toMinutes(seconds)
    val remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%01d:%02d", minutes, remainingSeconds)
}