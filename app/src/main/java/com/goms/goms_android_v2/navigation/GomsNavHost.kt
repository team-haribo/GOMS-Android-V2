package com.goms.goms_android_v2.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import com.goms.goms_android_v2.MainActivity
import com.goms.goms_android_v2.ui.GomsAppState
import com.goms.login.navigation.inputLoginScreen
import com.goms.login.navigation.loginRoute
import com.goms.login.navigation.loginScreen
import com.goms.login.navigation.navigateToInputLogin
import com.goms.login.navigation.navigateToLogin
import com.goms.main.navigation.lateListScreen
import com.goms.main.navigation.mainScreen
import com.goms.main.navigation.navigateToLateList
import com.goms.main.navigation.navigateToMain
import com.goms.main.navigation.navigateToOutingStatus
import com.goms.main.navigation.navigateToStudentManagement
import com.goms.main.navigation.outingStatusScreen
import com.goms.main.navigation.studentManagementScreen
import com.goms.model.enum.Authority
import com.goms.qrcode.navigation.navigateToQrGenerate
import com.goms.qrcode.navigation.navigateToQrScan
import com.goms.qrcode.navigation.qrcodeGenerateScreen
import com.goms.qrcode.navigation.qrcodeScanScreen
import com.goms.sign_up.navigation.navigateToNumber
import com.goms.sign_up.navigation.navigateToPassword
import com.goms.sign_up.navigation.navigateToSignUp
import com.goms.sign_up.navigation.numberScreen
import com.goms.sign_up.navigation.passwordScreen
import com.goms.sign_up.navigation.signUpScreen

@Composable
fun GomsNavHost(
    appState: GomsAppState,
    modifier: Modifier = Modifier,
    startDestination: String = loginRoute
) {
    val navController = appState.navController
    val signUpViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val mainViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginScreen(
            onSignUpClick = navController::navigateToSignUp,
            onInputLoginClick = navController::navigateToInputLogin
        )
        inputLoginScreen(
            onBackClick = navController::popBackStack,
            onMainClick = { appState.navigateToTopLevelDestination(TopLevelDestination.MAIN) }
        )
        signUpScreen(
            viewModelStoreOwner = signUpViewModelStoreOwner,
            onBackClick = navController::popBackStack,
            onNumberClick = navController::navigateToNumber
        )
        numberScreen(
            viewModelStoreOwner = signUpViewModelStoreOwner,
            onBackClick = navController::popBackStack,
            onPasswordClick = navController::navigateToPassword
        )
        passwordScreen(
            viewModelStoreOwner = signUpViewModelStoreOwner,
            onBackClick = navController::popBackStack,
            onLoginClick = { appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN) }
        )
        mainScreen(
            viewModelStoreOwner = mainViewModelStoreOwner,
            onOutingStatusClick = navController::navigateToOutingStatus,
            onLateListClick = navController::navigateToLateList,
            onStudentManagementClick = navController::navigateToStudentManagement,
            onQrcodeClick = { role ->
                if (role == Authority.ROLE_STUDENT) {
                    navController.navigateToQrScan()
                } else {
                    navController.navigateToQrGenerate()
                }
            }
        )
        qrcodeScanScreen(
            onPermissionBlock = navController::popBackStack
        )
        qrcodeGenerateScreen(
            onTimerFinish = navController::popBackStack,
            onRemoteError = navController::popBackStack
        )
        outingStatusScreen(
            viewModelStoreOwner = mainViewModelStoreOwner,
            onBackClick = navController::popBackStack
        )
        lateListScreen(
            viewModelStoreOwner = mainViewModelStoreOwner,
            onBackClick = navController::popBackStack
        )
        studentManagementScreen(
            viewModelStoreOwner = mainViewModelStoreOwner,
            onBackClick = navController::popBackStack
        )
    }
}