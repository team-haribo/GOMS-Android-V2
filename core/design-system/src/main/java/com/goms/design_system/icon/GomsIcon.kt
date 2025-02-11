package com.goms.design_system.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.goms.design_system.R

@Composable
fun GomsIcon(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_goms),
        contentDescription = stringResource(id = R.string.goms_icon_description),
        modifier = modifier
    )
}

@Composable
fun GomsTextIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_goms_text),
        contentDescription = stringResource(id = R.string.goms_text_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun GAuthIcon(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_gauth),
        contentDescription = stringResource(id = R.string.gauth_icon_description),
        modifier = modifier
    )
}

@Composable
fun BackIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = stringResource(id = R.string.back_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun ChevronRightIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_chevron_right),
        contentDescription = stringResource(id = R.string.chevron_right_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun ChevronDownIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_chevron_down),
        contentDescription = stringResource(id = R.string.chevron_down_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun ChevronUpIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_chevron_up),
        contentDescription = stringResource(id = R.string.chevron_up_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun CheckIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_check),
        contentDescription = stringResource(id = R.string.check_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun CloseIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_close),
        contentDescription = stringResource(id = R.string.close_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun SettingIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_setting),
        contentDescription = stringResource(id = R.string.setting_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun QrScanIcon(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_qr_scan),
        contentDescription = stringResource(id = R.string.qr_scan_icon_description),
        modifier = modifier
    )
}

@Composable
fun QrScanGuideIcon(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_qr_scan_guide),
        contentDescription = stringResource(id = R.string.qr_scan_guide_icon_description),
        modifier = modifier
    )
}

@Composable
fun QrCreateIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_qr_create),
        contentDescription = stringResource(id = R.string.qr_create_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun PersonIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_person),
        contentDescription = stringResource(id = R.string.person_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun SearchIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = stringResource(id = R.string.search_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun DeleteIcon(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_delete),
        contentDescription = stringResource(id = R.string.delete_icon_description),
        modifier = modifier
    )
}

@Composable
fun WriteIcon(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_write),
        contentDescription = stringResource(id = R.string.write_icon_description),
        modifier = modifier
    )
}

@Composable
fun FireIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_fire),
        contentDescription = stringResource(id = R.string.fire_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun CoffeeIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_coffee),
        contentDescription = stringResource(id = R.string.coffee_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun GalleryIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_gallery_add),
        contentDescription = stringResource(id = R.string.gallery_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun DefaultImageIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_default_image),
        contentDescription = stringResource(id = R.string.default_image_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun ForceOutingIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Image(
        painter = painterResource(id = R.drawable.ic_forceouting),
        contentDescription = stringResource(id = R.string.close_icon_description),
        modifier = modifier,
    )
}

@Composable
fun OutingIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_outing),
        contentDescription = stringResource(id = R.string.outing_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun ClockIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_clock),
        contentDescription = stringResource(id = R.string.clock_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun MenuIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_menu),
        contentDescription = stringResource(id = R.string.menu_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun InvisibleIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_invisible),
        contentDescription = stringResource(id = R.string.invisible_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun VisibleIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_visible),
        contentDescription = stringResource(id = R.string.visible_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun GomsLogoutIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_logout),
        contentDescription = stringResource(id = R.string.logout_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun GomsPasswordChangeIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_password_change),
        contentDescription = stringResource(id = R.string.password_change_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}

@Composable
fun GomsWithdrawalIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Image(
        painter = painterResource(id = R.drawable.ic_withdrawal),
        contentDescription = stringResource(id = R.string.withdrawal_icon_description),
        modifier = modifier,
        colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
    )
}