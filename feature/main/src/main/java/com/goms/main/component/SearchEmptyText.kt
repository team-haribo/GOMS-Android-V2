package com.goms.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goms.design_system.theme.GomsTheme

@Composable
fun SearchEmptyText() {
    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "\uD83E\uDD14",
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                color = colors.WHITE
            )
            Text(
                text = "검색 결과가 없습니다\n" +
                        "검색 내용이 잘못되진 않았나요?",
                style = typography.textMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.G4
            )
        }
    }
}