package com.goms.goms_android_v2.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import com.goms.goms_android_v2.ui.GomsAppState
import com.goms.login.navigation.inputLoginScreen
import com.goms.login.navigation.loginRoute
import com.goms.login.navigation.loginScreen
import com.goms.login.navigation.navigateToInputLogin
import com.goms.main.navigation.lateListScreen
import com.goms.main.navigation.mainScreen
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
import com.goms.re_password.navigation.emailCheckScreen
import com.goms.re_password.navigation.navigateToEmailCheck
import com.goms.re_password.navigation.navigateToPasswordNumber
import com.goms.re_password.navigation.navigateToRePassword
import com.goms.re_password.navigation.passwordNumberScreen
import com.goms.re_password.navigation.rePasswordScreen
import com.goms.setting.navigation.navigateToSettingScreen
import com.goms.setting.navigation.settingScreen
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

    val onErrorToast: (throwable: Throwable?, message: String?) -> Unit = { throwable, message ->
        val errorMessage = throwable?.let {
            when (it) {
                is ForBiddenException -> "학생회 권한인 학생만 요청 가능해요"
                is TimeOutException -> "서버 응답이 지연되고 있습니다, 잠시 후 다시 시도해주세요"
                is ServerException -> "서버 에러, 관리자에게 문의하세요"
                is NoInternetException -> "네트워크가 불안정합니다, 데이터나 와이파이 연결 상태를 확인해주세요"
                is OtherHttpException -> "알 수 없는 오류가 발생했습니다"
                is UnKnownException -> "예상치 못한 오류가 발생했습니다"
                else -> message
            }
        } ?: message ?: "오류가 발생했습니다"

        createToast(context, errorMessage)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        modifier = modifier
    ) {
        loginScreen(
            onSignUpClick = navController::navigateToSignUp,
            onInputLoginClick = navController::navigateToInputLogin
        )
        inputLoginScreen(
            onBackClick = navController::popBackStack,
            onMainClick = { appState.navigateToTopLevelDestination(TopLevelDestination.MAIN) },
            onRePasswordClick = navController::navigateToEmailCheck,
            onErrorToast = onErrorToast
        )
        signUpScreen(
            onBackClick = navController::popBackStack,
            onNumberClick = navController::navigateToNumber,
            onErrorToast = onErrorToast
        )
        numberScreen(
            onBackClick = navController::popBackStack,
            onPasswordClick = navController::navigateToPassword,
            onErrorToast = onErrorToast
        )
        passwordScreen(
            onBackClick = navController::popBackStack,
            onLoginClick = { appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN) },
            onErrorToast = onErrorToast
        )
        mainScreen(
            qrcodeState = qrcodeState,
            onOutingStatusClick = navController::navigateToOutingStatus,
            onLateListClick = navController::navigateToLateList,
            onStudentManagementClick = navController::navigateToStudentManagement,
            onQrcodeClick = { role ->
                if (role == Authority.ROLE_STUDENT) {
                    navController.navigateToQrScan()
                } else {
                    navController.navigateToQrGenerate()
                }
            },
            onErrorToast = onErrorToast,
            onSettingClick = navController::navigateToSettingScreen
        )
        qrcodeScanScreen(
            onPermissionBlock = navController::popBackStack,
            onSuccess = navController::popBackStack,
            onBackClick = navController::popBackStack,
        )
        qrcodeGenerateScreen(
            onTimerFinish = navController::popBackStack,
            onBackClick = navController::popBackStack,
            onRemoteError = navController::popBackStack,
            onErrorToast = onErrorToast
        )
        outingStatusScreen(
            onBackClick = navController::popBackStack,
            onErrorToast = onErrorToast
        )
        lateListScreen(
            onBackClick = navController::popBackStack,
            onErrorToast = onErrorToast
        )
        studentManagementScreen(
            onBackClick = navController::popBackStack,
            onErrorToast = onErrorToast
        )
        settingScreen(
            onBackClick = navController::popBackStack,
            onLogoutSuccess = onLogout,
            onErrorToast = onErrorToast,
            onEmailCheck = navController::navigateToEmailCheck,
            onUpdateAlarm = onUpdateAlarm,
            onThemeSelect = onThemeSelect
        )
        emailCheckScreen(
            onBackClick = navController::popBackStack,
            onNumberClick = navController::navigateToPasswordNumber,
            onErrorToast = onErrorToast
        )
        passwordNumberScreen(
            onBackClick = navController::popBackStack,
            onRePasswordClick = navController::navigateToRePassword,
            onErrorToast = onErrorToast
        )
        rePasswordScreen(
            onBackClick = navController::popBackStack,
            onSuccessClick = { appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN) },
            onErrorToast = onErrorToast
        )
    }
}