package com.goms.design_system.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.R
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.icon.DefaultImageIcon
import com.goms.design_system.icon.GalleryIcon
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileBottomSheet(
    modifier: Modifier,
    closeSheet: () -> Unit,
    onGalleryClick: () -> Unit,
    onDefaultImageClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { closeSheet() },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        containerColor = colors.G1
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            BottomSheetHeader(
                modifier = Modifier,
                closeSheet = closeSheet,
                title = stringResource(id = R.string.change_profile_image),
            )
            ProfileBottomSheetComponent(
                modifier = Modifier.padding(8.dp),
                onClick = { onGalleryClick() },
                content = stringResource(id = R.string.select_gallery),
                icon = { GalleryIcon(tint = colors.WHITE) }
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = colors.WHITE.copy(0.15f)
            )
            ProfileBottomSheetComponent(
                modifier = Modifier.padding(8.dp),
                onClick = { onDefaultImageClick() },
                content = stringResource(id = R.string.use_default_profile),
                icon = { DefaultImageIcon(tint = colors.WHITE) }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileBottomSheetComponent(
    modifier: Modifier,
    onClick: () -> Unit,
    content: String,
    icon: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .height(72.dp)
            .fillMaxWidth()
            .gomsClickable (
                isIndication = true,
                rippleColor = colors.G7.copy(0.5f)
            ) { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = content,
            style = typography.textMedium,
            color = colors.WHITE,
            fontWeight = FontWeight.SemiBold
        )
        icon()
    }
}