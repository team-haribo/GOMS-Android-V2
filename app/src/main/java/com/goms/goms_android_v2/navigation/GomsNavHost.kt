package com.goms.goms_android_v2.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.goms.common.exception.ForBiddenException
import com.goms.common.exception.NoInternetException
import com.goms.common.exception.OtherHttpException
import com.goms.common.exception.ServerException
import com.goms.common.exception.TimeOutException
import com.goms.common.exception.UnKnownException
import com.goms.find_password.navigation.emailCheckScreen
import com.goms.find_password.navigation.findPasswordScreen
import com.goms.find_password.navigation.navigateToEmailCheck
import com.goms.find_password.navigation.navigateToFindPassword
import com.goms.find_password.navigation.navigateToPasswordNumber
import com.goms.find_password.navigation.passwordNumberScreen
import com.goms.goms_android_v2.R
import com.goms.goms_android_v2.ui.GomsAppState
import com.goms.login.navigation.inputLoginScreen
import com.goms.login.navigation.loginRoute
import com.goms.login.navigation.loginScreen
import com.goms.login.navigation.navigateToInputLogin
import com.goms.main.navigation.adminMenuScreen
import com.goms.main.navigation.lateListScreen
import com.goms.main.navigation.mainRoute
import com.goms.main.navigation.mainScreen
import com.goms.main.navigation.navigateToAdminMenu
import com.goms.main.navigation.navigateToLateList
import com.goms.main.navigation.navigateToOutingStatus
import com.goms.main.navigation.navigateToStudentManagement
import com.goms.main.navigation.outingStatusScreen
import com.goms.main.navigation.studentManagementScreen
import com.goms.model.enum.Authority
import com.goms.qrcode.navigation.navigateToQrGenerate
import com.goms.qrcode.navigation.navigateToQrScan
import com.goms.qrcode.navigation.qrcodeGenerateScreen
import com.goms.qrcode.navigation.qrcodeScanScreen
import com.goms.re_password.navigation.navigateToPasswordCheck
import com.goms.re_password.navigation.navigateToRePassword
import com.goms.re_password.navigation.passwordCheckScreen
import com.goms.re_password.navigation.rePasswordScreen
import com.goms.setting.navigation.navigateToSettingScreen
import com.goms.setting.navigation.navigateToWithdrawalScreen
import com.goms.setting.navigation.settingScreen
import com.goms.setting.navigation.withdrawalScreen
import com.goms.sign_up.navigation.navigateToNumber
import com.goms.sign_up.navigation.navigateToPassword
import com.goms.sign_up.navigation.navigateToSignUp
import com.goms.sign_up.navigation.numberScreen
import com.goms.sign_up.navigation.passwordScreen
import com.goms.sign_up.navigation.signUpScreen
import com.goms.ui.createToast

@Composable
fun GomsNavHost(
    appState: GomsAppState,
    qrcodeState: String,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    onThemeSelect: () -> Unit,
    onUpdateAlarm: (String) -> Unit,
    startDestination: String = loginRoute
) {
    val context = LocalContext.current
    val navController = appState.navController

    val onErrorToast: (throwable: Throwable?, message: Int?) -> Unit = { throwable, message ->
        val errorMessage = throwable?.let {
            when (it) {
                is ForBiddenException -> R.string.error_for_bidden
                is TimeOutException -> R.string.error_time_out
                is ServerException -> R.string.error_server
                is NoInternetException -> R.string.error_no_internet
                is OtherHttpException -> R.string.error_other_http
                is UnKnownException -> R.string.error_un_known
                else -> message
            }
        } ?: message ?: R.string.error_default

        createToast(context, context.getString(errorMessage))
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            if (targetState.destination.route != mainRoute) {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            } else {
                EnterTransition.None
            }
        },
        exitTransition = {
            if (targetState.destination.route != mainRoute) {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            } else {
                ExitTransition.None
            }
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        modifier = modifier
    ) {
        loginScreen(
            onSignUpClick = navController::navigateToSignUp,
            onInputLoginClick = navController::navigateToInputLogin
        )
        inputLoginScreen(
            onBackClick = navController::navigateUp,
            onMainClick = { appState.navigateToTopLevelDestination(TopLevelDestination.MAIN) },
            onRePasswordClick = navController::navigateToEmailCheck,
            onErrorToast = onErrorToast
        )

        signUpScreen(
            onBackClick = navController::navigateUp,
            onNumberClick = navController::navigateToNumber,
            onErrorToast = onErrorToast
        )
        numberScreen(
            onBackClick = navController::navigateUp,
            onPasswordClick = navController::navigateToPassword,
            onErrorToast = onErrorToast
        )
        passwordScreen(
            onBackClick = navController::navigateUp,
            onLoginClick = { appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN) },
            onErrorToast = onErrorToast
        )

        mainScreen(
            qrcodeState = qrcodeState,
            onOutingStatusClick = navController::navigateToOutingStatus,
            onLateListClick = navController::navigateToLateList,
            onQrcodeClick = { role ->
                if (role == Authority.ROLE_STUDENT) {
                    navController.navigateToQrScan()
                } else {
                    navController.navigateToQrGenerate()
                }
            },
            onSettingClick = navController::navigateToSettingScreen,
            onAdminMenuClick = navController::navigateToAdminMenu,
            onErrorToast = onErrorToast
        )
        outingStatusScreen(
            onBackClick = navController::navigateUp,
            onErrorToast = onErrorToast
        )
        lateListScreen(
            onBackClick = navController::navigateUp,
            onErrorToast = onErrorToast
        )
        studentManagementScreen(
            onBackClick = navController::navigateUp,
            onErrorToast = onErrorToast
        )
        adminMenuScreen(
            onBackClick = navController::navigateUp,
            onQrCreateClick = navController::navigateToQrGenerate,
            onStudentManagementClick = navController::navigateToStudentManagement,
            onOutingStatusClick = navController::navigateToOutingStatus,
            onLateListClick = navController::navigateToLateList,
            onSettingClick = navController::navigateToSettingScreen
        )
        qrcodeScanScreen(
            onPermissionBlock = navController::navigateUp,
            onSuccess = navController::navigateUp,
            onBackClick = navController::navigateUp,
        )
        qrcodeGenerateScreen(
            onTimerFinish = navController::navigateUp,
            onBackClick = navController::navigateUp,
            onRemoteError = navController::navigateUp,
            onErrorToast = onErrorToast
        )
        settingScreen(
            onBackClick = navController::navigateUp,
            onLogoutSuccess = onLogout,
            onErrorToast = onErrorToast,
            onPasswordCheck = navController::navigateToPasswordCheck,
            onUpdateAlarm = onUpdateAlarm,
            onThemeSelect = onThemeSelect,
            onWithdrawalClick = navController::navigateToWithdrawalScreen
        )
        withdrawalScreen(
            onBackClick = navController::navigateUp,
            onWithdrawal = onLogout,
            onErrorToast = onErrorToast
        )
        emailCheckScreen(
            onBackClick = navController::navigateUp,
            onNumberClick = navController::navigateToPasswordNumber,
            onErrorToast = onErrorToast
        )
        passwordNumberScreen(
            onBackClick = navController::navigateUp,
            onFindPasswordClick = navController::navigateToFindPassword,
            onErrorToast = onErrorToast
        )
        findPasswordScreen(
            onBackClick = navController::navigateUp,
            onSuccessClick = { appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN) },
            onErrorToast = onErrorToast
        )
        passwordCheckScreen(
            onBackClick = navController::navigateUp,
            onRePasswordClick = navController::navigateToRePassword
        )
        rePasswordScreen(
            onBackClick = navController::navigateUp,
            onSuccessClick = { appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN) },
            onErrorToast = onErrorToast
        )
    }
}