package com.goms.goms_android_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.goms.common.result.Result
import com.goms.goms_android_v2.ui.GomsApp
import com.goms.ui.createToast
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
        checkAppUpdate()
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        viewModel.tokenRefresh()

        lifecycleScope.launch {
            viewModel.uiState
                .collect {
                    uiState = it
                }
        }

        setContent {
            installSplashScreen().apply {
                setKeepOnScreenCondition {
                    uiState is MainActivityUiState.Loading
                }
            }
            runBlocking {
                viewModel.getSettingInfo()
            }
            if (uiState !is MainActivityUiState.Loading) {
                GomsApp(
                    windowSizeClass = calculateWindowSizeClass(this),
                    onLogout = { logout() },
                    onAlarmOff = { viewModel.deleteDeviceToken() },
                    onAlarmOn = { getNotification() },
                    uiState = uiState,
                )
            }
        }
    }

    private fun checkAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            val isUpdateAvailable = appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)

            if (isUpdateAvailable && isUpdateAllowed) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    0,
                )
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
            createToast(this, "'뒤로'버튼 한번 더 누르시면 종료됩니다.")
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

    private fun logout() {
        runBlocking {
            viewModel.deleteToken()
        }
        finish()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}