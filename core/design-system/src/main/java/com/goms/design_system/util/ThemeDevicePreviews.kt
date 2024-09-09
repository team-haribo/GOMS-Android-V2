package com.goms.design_system.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme - phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme - phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme - landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme - landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme - foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme - foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme - tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme - tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
annotation class ThemeDevicePreviews
