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
import com.goms.analytics.AnalyticsHelper
import com.goms.goms_android_v2.ui.GomsApp
import com.goms.ui.createToast
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var doubleBackToExitPressedOnce = false
    private var backPressedTimestamp = 0L
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            controlTheStackWhenBackPressed()
        }
    }

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAppUpdate()
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            viewModel.uiState
                .collect {
                    uiState = it
                    if (it is MainActivityUiState.Success) runBlocking { viewModel.saveToken(it.loginResponseModel) }
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
                    onAlarmOn = { saveNotification() },
                    uiState = uiState,
                    analyticsHelper = analyticsHelper
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

    private fun controlTheStackWhenBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (doubleBackToExitPressedOnce && currentTime - backPressedTimestamp <= 2_000L) {
            finishAffinity()
        } else {
            doubleBackToExitPressedOnce = true
            backPressedTimestamp = currentTime
            createToast(this, getString(R.string.close_app))
        }
    }

    private fun saveNotification() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val deviceToken = task.result
                viewModel.saveDeviceToken(deviceToken = deviceToken)
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