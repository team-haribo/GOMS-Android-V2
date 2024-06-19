package com.goms.design_system.component.dialog

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.theme.ThemeType

@Composable
fun GomsTwoButtonDialog(
    openDialog: Boolean,
    onStateChange: (Boolean) -> Unit,
    title: String,
    content: String,
    dismissText: String,
    checkText: String,
    onDismissClick: () -> Unit,
    onCheckClick: () -> Unit
) {
    var openDialog by remember { mutableStateOf(openDialog) }

    if (openDialog) {
        Dialog(onDismissRequest = { openDialog = false }) {
            Card(
                modifier = Modifier.width(280.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.background(colors.G1)) {
                    Column(
                        modifier = Modifier
                            .background(colors.G1)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier.fillMaxWidth(),
                            color = colors.WHITE,
                            style = typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                        )
                        GomsSpacer(size = SpacerSize.ExtraSmall)
                        Text(
                            text = content,
                            modifier = Modifier.fillMaxWidth(),
                            color = colors.G7,
                            style = typography.textMedium,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(end = 8.dp)
                            .background(colors.G1),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp),
                            onClick = {
                                openDialog = false
                                onDismissClick()
                            }
                        ) {
                            Text(
                                text = dismissText,
                                color = colors.I5,
                                style = typography.textMedium,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        TextButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp),
                            onClick = {
                                openDialog = false
                                onCheckClick()
                            }
                        ) {
                            Text(
                                text = checkText,
                                color = colors.N5,
                                style = typography.textMedium,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    }
                }
            }
        }
    } else {
        onStateChange(openDialog)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun GomsTwoButtonDialogPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        GomsTwoButtonDialog(
            openDialog = true,
            onStateChange = {},
            title = "GOMS",
            content = "연어사냥하는 불곰",
            dismissText = "취소",
            checkText = "먹기",
            onDismissClick = {},
            onCheckClick = {}
        )
    }
}