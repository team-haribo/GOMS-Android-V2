package com.goms.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goms.design_system.theme.GomsTheme

@Composable
fun LoginText() {
    GomsTheme { colors, typography ->  
        Column {
            Row {
                Text(
                    text = "수요 외출제",
                    style = typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.P5
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "관리 서비스",
                    style = typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.WHITE
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "앱으로 간편하게 GSM의\n" +
                        "수요 외출제를 이용해 보세요!",
                style = typography.textMedium,
                fontWeight = FontWeight.Normal,
                color = colors.G4,
                textAlign = TextAlign.Center
            )
        }
    }
}