package com.template.evilgodxu.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.template.evilgodxu.infrastructure.adaptive.rememberWindowSizeClass
import com.template.evilgodxu.screen.home.landscape.LandscapeAssembly
import com.template.evilgodxu.screen.home.portrait.PortraitAssembly
import org.koin.androidx.compose.koinViewModel

// 路由入口：根据 WindowSizeClass 调用对应 Assembly
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by homeViewModel.state.collectAsStateWithLifecycle()
    val windowSizeClass = rememberWindowSizeClass()

    if (windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        LandscapeAssembly(
            modifier = modifier,
            uiState = uiState,
            onThemeModeChange = { homeViewModel.setThemeMode(it) },
            onLanguageChange = { homeViewModel.setLanguage(it) },
            onTabSelected = { homeViewModel.selectTab(it) },
        )
    } else {
        PortraitAssembly(
            modifier = modifier,
            uiState = uiState,
            onShowSettings = { homeViewModel.showSettings() },
            onHideSettings = { homeViewModel.hideSettings() },
            onThemeModeChange = { homeViewModel.setThemeMode(it) },
            onLanguageChange = { homeViewModel.setLanguage(it) },
        )
    }
}
