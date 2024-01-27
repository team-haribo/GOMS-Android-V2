package com.goms.goms_android_v2.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.goms.goms_android_v2.navigation.TopLevelDestination
import com.goms.login.navigation.loginRoute
import com.goms.login.navigation.navigateToLogin
import com.goms.main.navigation.navigateToMain
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberBitgoeulAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) : GomsAppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass
    ) {
        GomsAppState(
            navController,
            coroutineScope,
            windowSizeClass
        )
    }
}

@Stable
class GomsAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLeverDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            loginRoute -> TopLevelDestination.LOGIN
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }

            when (topLevelDestination) {
                TopLevelDestination.LOGIN -> navController.navigateToLogin(topLevelNavOptions)
                TopLevelDestination.MAIN -> navController.navigateToMain(topLevelNavOptions)
            }
        }
    }
}