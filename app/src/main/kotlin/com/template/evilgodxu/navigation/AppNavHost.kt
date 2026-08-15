package com.template.evilgodxu.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowSizeClass
import com.template.evilgodxu.screens.home.HomeScreen
import com.template.evilgodxu.screens.settings.SettingsScreen
import com.template.evilgodxu.theme.rememberWindowSizeClass

// 导航宿主：宽屏走统一桌面外壳，窄屏走路由栈
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
) {
    val windowSizeClass = rememberWindowSizeClass()
    if (windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        AppShell(modifier = modifier)
        return
    }

    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = modifier,
        entryProvider = { key ->
            when (key) {
                is Home -> NavEntry(key) { HomeScreen(onOpenSettings = { backStack.add(Settings) }) }
                is Settings -> NavEntry(key) {
                    SettingsScreen(onBack = { backStack.removeLastOrNull() })
                }
                else -> error("Unknown NavKey: $key")
            }
        },
    )
}