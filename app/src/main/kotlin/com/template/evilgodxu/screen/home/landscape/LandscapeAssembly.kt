package com.template.evilgodxu.screen.home.landscape

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.template.evilgodxu.screen.home.HomeUiState
import com.template.evilgodxu.screen.home.landscape.left_panel.LandscapeTab
import com.template.evilgodxu.screen.home.landscape.left_panel.LeftPanel
import com.template.evilgodxu.screen.home.landscape.main_workspace.MainWorkspace

// 宽屏页面组装入口：左侧导航面板 + 右侧主工作区
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandscapeAssembly(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onThemeModeChange: (String) -> Unit = {},
    onLanguageChange: (String) -> Unit = {},
    onTabSelected: (LandscapeTab) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding),
        ) {
            LeftPanel(
                selectedTab = uiState.selectedTab,
                onTabSelected = onTabSelected,
            )

            MainWorkspace(
                modifier = Modifier.weight(1f),
                selectedTab = uiState.selectedTab,
                themeMode = uiState.themeMode,
                language = uiState.language,
                onThemeModeChange = onThemeModeChange,
                onLanguageChange = onLanguageChange,
            )
        }
    }
}
