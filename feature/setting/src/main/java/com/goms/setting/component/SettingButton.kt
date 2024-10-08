package com.goms.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.icon.ChevronRightIcon
import com.goms.design_system.icon.GomsLogoutIcon
import com.goms.design_system.icon.GomsPasswordChangeIcon
import com.goms.design_system.icon.GomsWithdrawalIcon
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

enum class SettingButtonType(val value: String, val kr: String) {
    PasswordChange("PasswordChange", "비밀번호 재설정"),
    Logout("Logout", "로그아웃"),
    Withdrawal("Withdrawal", "회원 탈퇴")
}
@Composable
internal fun SettingButton(
    modifier: Modifier,
    buttonType: String,
    onClick: () -> Unit
) {
    val text = when(buttonType) {
        SettingButtonType.PasswordChange.value -> SettingButtonType.PasswordChange.kr
        SettingButtonType.Logout.value -> SettingButtonType.Logout.kr
        SettingButtonType.Withdrawal.value -> SettingButtonType.Withdrawal.kr
        else -> SettingButtonType.PasswordChange.kr
    }

    Column(
        modifier = modifier
            .gomsClickable(
                isIndication = true,
                rippleColor = colors.G7.copy(0.5f)
            ) {
                onClick()
            }
    ) {
        GomsSpacer(size = SpacerSize.Medium)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                when(buttonType) {
                    SettingButtonType.PasswordChange.value -> GomsPasswordChangeIcon(tint = colors.WHITE)
                    SettingButtonType.Logout.value -> GomsLogoutIcon(tint = colors.WHITE)
                    SettingButtonType.Withdrawal.value -> GomsWithdrawalIcon(tint = colors.WHITE)
                }
                Text(
                    text = text,
                    style = typography.textMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.WHITE
                )
            }
            ChevronRightIcon(
                tint = colors.WHITE
            )
        }
        GomsSpacer(size = SpacerSize.Medium)
    }
}
