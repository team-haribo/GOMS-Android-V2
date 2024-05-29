package com.goms.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.icon.BackIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.model.enum.Authority

@Composable
fun GomsRoleBackButton(
    modifier: Modifier = Modifier.height(48.dp),
    role: Authority,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(start = 15.dp)
            .gomsClickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackIcon(tint = if (role == Authority.ROLE_STUDENT_COUNCIL) colors.A7 else colors.P5)
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = stringResource(id = R.string.go_back),
            style = GomsTheme.typography.textMedium,
            fontWeight = FontWeight.Normal,
            color = if (role == Authority.ROLE_STUDENT_COUNCIL) colors.A7 else colors.P5
        )
    }
}