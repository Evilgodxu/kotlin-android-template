package com.template.evilgodxu.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.template.evilgodxu.screens.home.compact.CompactAssembly
import com.template.evilgodxu.screens.home.expanded.ExpandedAssembly
import com.template.evilgodxu.theme.rememberWindowSizeClass
import org.koin.androidx.compose.koinViewModel

// 根据 WindowSizeClass 选择紧凑或展开布局
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by homeViewModel.state.collectAsStateWithLifecycle()
    val windowSizeClass = rememberWindowSizeClass()

    if (windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        ExpandedAssembly(
            modifier = modifier,
            uiState = uiState,
            onTabSelected = { homeViewModel.selectTab(it) },
        )
    } else {
        CompactAssembly(
            modifier = modifier,
            uiState = uiState,
            onShowSettings = { homeViewModel.showSettings() },
            onHideSettings = { homeViewModel.hideSettings() },
        )
    }
}
