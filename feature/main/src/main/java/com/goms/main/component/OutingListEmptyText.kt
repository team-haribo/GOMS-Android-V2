package com.goms.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.icon.CoffeeIcon
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

@Composable
fun OutingListEmptyText() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CoffeeIcon(
            modifier = Modifier.size(80.dp),
            tint = colors.G4
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "텅 비어있네요... 다들 바쁜가 봐요!",
            style = typography.textSmall,
            fontWeight = FontWeight.SemiBold,
            color = colors.G4
        )
    }
}
