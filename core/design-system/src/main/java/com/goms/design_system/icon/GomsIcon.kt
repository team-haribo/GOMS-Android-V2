package com.goms.design_system.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.goms.design_system.R

@Composable
fun GomsIcon(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_goms),
        contentDescription = "Miso Color Icon",
        modifier = modifier
    )
}