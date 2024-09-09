package com.goms.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.goms.analytics.AnalyticsEvent
import com.goms.analytics.AnalyticsEvent.Param
import com.goms.analytics.AnalyticsEvent.ParamKeys
import com.goms.analytics.AnalyticsEvent.Types
import com.goms.analytics.AnalyticsHelper
import com.goms.analytics.LocalAnalyticsHelper

fun AnalyticsHelper.logScreenView(screenName: String) {
    logEvent(
        AnalyticsEvent(
            type = Types.SCREEN_VIEW,
            extras = listOf(
                Param(ParamKeys.SCREEN_NAME, screenName),
            ),
        ),
    )
}

@Composable
fun TrackScreenViewEvent(
    screenName: String,
    analyticsHelper: AnalyticsHelper = LocalAnalyticsHelper.current,
) = DisposableEffect(Unit) {
    analyticsHelper.logScreenView(screenName)
    onDispose {}
}