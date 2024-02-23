package com.goms.goms_android_v2

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.goms.common.result.Result
import com.goms.design_system.theme.GomsTheme
import com.goms.goms_android_v2.ui.GomsApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var doubleBackToExitPressedOnce = false
    private var backPressedTimestamp = 0L
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            controlTheStackWhenBackPressed()
        }
    }

    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collectLatest {
                        if (it is MainActivityUiState.Success) {
                            getNotification()
                        }
                    }
            }
        }

        setContent {
            installSplashScreen().apply {
                setKeepOnScreenCondition {
                    uiState is MainActivityUiState.Loading
                }
            }
            when (uiState) {
                is MainActivityUiState.Success -> {
                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestMultiplePermissions()
                    ) { isGrantedMap: Map<String, Boolean> -> }

                    LaunchedEffect(true) {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                        )
                    }
                    CompositionLocalProvider {
                        GomsTheme { _, _ ->
                            GomsApp(
                                windowSizeClass = calculateWindowSizeClass(this),
                                uiState = uiState
                            )
                        }
                    }
                }
                is MainActivityUiState.Error -> {
                    CompositionLocalProvider {
                        GomsTheme { _, _ ->
                            GomsApp(
                                windowSizeClass = calculateWindowSizeClass(this),
                                uiState = uiState
                            )
                        }
                    }
                }
                else -> Unit
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
            Toast.makeText(this, "'뒤로'버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNotification() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val deviceTokenSF = getSharedPreferences("deviceToken", MODE_PRIVATE)
                val deviceToken = task.result
                if (deviceTokenSF.getString("device", "") == deviceToken) {
                    viewModel.saveDeviceToken(deviceToken = deviceToken)
                    setNotification(deviceToken = deviceToken)
                }
            }
        }
    }

    private fun setNotification(deviceToken: String) {
        lifecycleScope.launch {
            viewModel.saveDeviceTokenUiState.collect {
                when (it) {
                    is Result.Success -> {
                        val deviceTokenSF = getSharedPreferences("deviceToken", MODE_PRIVATE)
                        deviceTokenSF.edit().putString("device", deviceToken).apply()
                    }
                    is Result.Error, Result.Loading -> Unit
                }
            }
        }
    }
}