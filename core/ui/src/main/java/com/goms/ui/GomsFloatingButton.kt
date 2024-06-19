package com.goms.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.icon.QrCreateIcon
import com.goms.design_system.icon.QrScanIcon
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.gomsPreview
import com.goms.model.enum.Authority

@Composable
fun GomsFloatingButton(
    modifier: Modifier = Modifier,
    role: Authority,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier.size(64.dp),
        containerColor = if (role == Authority.ROLE_STUDENT) colors.P5 else colors.A7,
        onClick = { onClick() },
        shape = CircleShape
    ) {
        when (role) {
            Authority.ROLE_STUDENT -> QrScanIcon()
            Authority.ROLE_STUDENT_COUNCIL -> QrCreateIcon()
        }
    }
}

@Preview
@Composable
fun GomsFloatingButtonPreview() {
    gomsPreview {
        Column {
            GomsFloatingButton(role = Authority.ROLE_STUDENT) {}
            GomsFloatingButton(role = Authority.ROLE_STUDENT_COUNCIL) {}
        }
    }
}