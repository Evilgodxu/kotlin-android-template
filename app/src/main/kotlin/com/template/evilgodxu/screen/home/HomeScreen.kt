package com.template.evilgodxu.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.template.evilgodxu.infrastructure.adaptive.rememberWindowWidthSizeClass
import com.template.evilgodxu.screen.home.landscape.LandscapeAssembly
import com.template.evilgodxu.screen.home.portrait.PortraitAssembly
import org.koin.androidx.compose.koinViewModel

// 路由入口：根据 WindowWidthSizeClass 调用对应 Assembly
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by homeViewModel.state.collectAsStateWithLifecycle()
    val windowWidthSizeClass = rememberWindowWidthSizeClass()

    when (windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> PortraitAssembly(
            modifier = modifier,
            uiState = uiState,
        )
        else -> LandscapeAssembly(
            modifier = modifier,
            uiState = uiState,
        )
    }
}
