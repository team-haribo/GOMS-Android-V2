package com.goms.qrcode.util

import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.view.PreviewView

/**
 * CameraX의 Preview 객체를 생성하고 설정하는 함수입니다.
 * 이 함수는 주어진 PreviewView에 카메라 프리뷰를 연결하며,
 * 해상도와 화면 비율 설정을 포함합니다.
 */

internal fun buildPreview(previewView: PreviewView): Preview {
    return Preview.Builder()
        .setResolutionSelector(
            ResolutionSelector.Builder()
                .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
                .build()
        )
        .build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }
}