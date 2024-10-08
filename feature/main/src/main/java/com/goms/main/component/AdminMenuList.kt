package com.goms.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.icon.ChevronRightIcon
import com.goms.design_system.icon.ClockIcon
import com.goms.design_system.icon.OutingIcon
import com.goms.design_system.icon.PersonIcon
import com.goms.design_system.icon.QrCreateIcon
import com.goms.design_system.icon.SettingIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.main.R

@Composable
internal fun AdminMenuList(
    modifier: Modifier = Modifier,
    onQrCreateClick: () -> Unit,
    onStudentManagementClick: () -> Unit,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AdminMenuListItem(
                icon = {
                    QrCreateIcon(
                        modifier = Modifier.size(24.dp),
                        tint = colors.G7
                    )
                },
                text = stringResource(id = R.string.qr_create),
            ) { onQrCreateClick() }
            AdminMenuListItem(
                icon = {
                    PersonIcon(
                        modifier = Modifier.size(24.dp),
                        tint = colors.G7
                    )
                },
                text = stringResource(id = R.string.student_management),
            ) { onStudentManagementClick() }
            AdminMenuListItem(
                icon = {
                    OutingIcon(
                        modifier = Modifier.size(24.dp),
                        tint = colors.G7
                    )
                },
                text = stringResource(id = R.string.outing_status),
            ) { onOutingStatusClick() }
            AdminMenuListItem(
                icon = {
                    ClockIcon(
                        modifier = Modifier.size(24.dp),
                        tint = colors.G7
                    )
                },
                text = stringResource(id = R.string.late_list),
            ) { onLateListClick() }
            AdminMenuListItem(
                icon = {
                    SettingIcon(
                        modifier = Modifier.size(24.dp),
                        tint = colors.G7
                    )
                },
                text = stringResource(id = R.string.setting),
            ) { onSettingClick() }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = colors.WHITE.copy(0.15f)
            )
        }
    }
}

@Composable
private fun AdminMenuListItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    text: String,
    onItemClick: () -> Unit
) {
    Column(
        modifier = modifier
            .gomsClickable(
                isIndication = true,
                rippleColor = colors.G7.copy(0.5f)
            ) {
                onItemClick()
            }
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = colors.WHITE.copy(0.15f)
        )
        GomsSpacer(height = 22.dp)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                icon()
                Text(
                    text = text,
                    style = GomsTheme.typography.textMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.G7
                )
            }
            ChevronRightIcon(
                tint = colors.G7
            )
        }
        GomsSpacer(height = 22.dp)
    }
}