package com.goms.main

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.ThemeType
import com.goms.main.component.AdminMenuList
import com.goms.main.component.AdminMenuText
import com.goms.model.enum.Authority
import com.goms.ui.GomsRoleBackButton

@Composable
internal fun AdminMenuRoute(
    onBackClick: () -> Unit,
    onQrCreateClick: () -> Unit,
    onStudentManagementClick: () -> Unit,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    AdminMenuScreen(
        onBackClick = onBackClick,
        onQrCreateClick = onQrCreateClick,
        onStudentManagementClick = onStudentManagementClick,
        onOutingStatusClick = onOutingStatusClick,
        onLateListClick = onLateListClick,
        onSettingClick = onSettingClick
    )
}

@Composable
private fun AdminMenuScreen(
    role: Authority = Authority.ROLE_STUDENT_COUNCIL,
    onBackClick: () -> Unit,
    onQrCreateClick: () -> Unit,
    onStudentManagementClick: () -> Unit,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GomsTheme.colors.BACKGROUND)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        GomsRoleBackButton(role = role) {
            onBackClick()
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AdminMenuText(modifier = Modifier.align(Alignment.Start))
            GomsSpacer(size = SpacerSize.Small)
            AdminMenuList(
                onQrCreateClick = onQrCreateClick,
                onStudentManagementClick = onStudentManagementClick,
                onOutingStatusClick = onOutingStatusClick,
                onLateListClick = onLateListClick,
                onSettingClick = onSettingClick
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AdminMenuScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        AdminMenuScreen(
            onBackClick = {},
            onQrCreateClick = {},
            onStudentManagementClick = {},
            onOutingStatusClick = {},
            onLateListClick = {},
        ) {}
    }
}