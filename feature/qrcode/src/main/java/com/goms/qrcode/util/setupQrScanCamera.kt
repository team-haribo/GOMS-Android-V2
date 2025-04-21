package com.goms.qrcode.util

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

/**
 * QR 코드 스캔을 위한 카메라를 설정하는 함수.
 *
 * @param previewView 카메라 프리뷰를 표시할 PreviewView.
 * @param lifecycleOwner 카메라의 생명주기를 관리할 LifecycleOwner.
 * @param onQrcodeScanned QR 코드가 감지될 때 호출될 콜백 함수.
 */
internal fun setupQrScanCamera(
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    onQrcodeScanned: (String) -> Unit
) {
    // PreviewView의 컨텍스트 가져오기
    val context = previewView.context

    // CameraX의 ProcessCameraProvider 인스턴스 가져오기
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    // 카메라 제공자 객체가 준비되었을 때 실행될 리스너 등록
    cameraProviderFuture.addListener({
        // CameraProvider 객체 가져오기
        val cameraProvider = cameraProviderFuture.get()

        try {
            // 후면 카메라를 선택
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // 카메라 프리뷰 설정 (buildPreview는 프리뷰를 생성하는 함수)
            val preview = buildPreview(previewView)

            // 이미지 분석기 설정 (QR 코드 감지 시 onQrcodeScanned 콜백 호출)
            val imageAnalyzer = buildImageAnalysis(context) { qrCodeData ->
                qrCodeData?.takeIf { it.isNotEmpty() }?.let { onQrcodeScanned(it) }
            }

            // 기존에 바인딩된 카메라 해제 (중복 실행 방지)
            cameraProvider.unbindAll()

            // 카메라를 라이프사이클과 바인딩하여 프리뷰 및 분석 기능 활성화
            cameraProvider.bindToLifecycle(
                lifecycleOwner,  // 생명주기 소유자
                cameraSelector,  // 사용할 카메라 (후면 카메라)
                preview,         // 프리뷰 설정
                imageAnalyzer    // QR 코드 분석 기능
            )
        } catch (e: Exception) {

            Log.e("QrcodeScanView", "Camera binding failed", e)
        }
    }, ContextCompat.getMainExecutor(context)) // 메인 스레드에서 실행되도록 설정
}