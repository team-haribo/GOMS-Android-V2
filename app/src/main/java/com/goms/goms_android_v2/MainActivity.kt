package com.goms.goms_android_v2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.goms.design_system.theme.GomsTheme
import com.goms.goms_android_v2.ui.GomsApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var doubleBackToExitPressedOnce = false
    private var backPressedTimestamp = 0L
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            controlTheStackWhenBackPressed()
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        setContent {
            installSplashScreen()
            CompositionLocalProvider {
                GomsTheme { _, _ ->
                    GomsApp(windowSizeClass = calculateWindowSizeClass(this))
                }
            }
        }
    }

    fun controlTheStackWhenBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (doubleBackToExitPressedOnce && currentTime - backPressedTimestamp <= 2000) {
            finishAffinity()
        } else {
            doubleBackToExitPressedOnce = true
            backPressedTimestamp = currentTime
            Toast.makeText(this, "'뒤로' 버튼 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}