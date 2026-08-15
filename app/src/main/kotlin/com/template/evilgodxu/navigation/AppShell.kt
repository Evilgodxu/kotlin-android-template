package com.template.evilgodxu.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.template.evilgodxu.R
import com.template.evilgodxu.screens.home.HomeViewModel
import com.template.evilgodxu.screens.home.expanded.HomeExpandedAssembly
import com.template.evilgodxu.screens.settings.SettingsViewModel
import com.template.evilgodxu.screens.settings.expanded.SettingsExpandedAssembly
import com.template.evilgodxu.theme.LocalThemeTransitionController
import org.koin.androidx.compose.koinViewModel

// 桌面模式导航项
private enum class AppTab { HOME, SETTINGS }

// 统一外壳：持久侧边栏 + 内容区切换，宽屏桌面交互
@Composable
fun AppShell(modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = koinViewModel()
    val settingsViewModel: SettingsViewModel = koinViewModel()
    val homeUiState by homeViewModel.state.collectAsStateWithLifecycle()
    val settingsUiState by settingsViewModel.uiState.collectAsStateWithLifecycle()
    val onThemeClick: (Offset) -> Unit = LocalThemeTransitionController.current::revealAt
    var selectedTab by rememberSaveable { mutableStateOf(AppTab.HOME) }

    Row(modifier = modifier.fillMaxSize()) {
        AppRail(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
        )
        when (selectedTab) {
            AppTab.HOME -> HomeExpandedAssembly(
                uiState = homeUiState,
                modifier = Modifier.weight(1f).fillMaxSize(),
            )
            AppTab.SETTINGS -> SettingsExpandedAssembly(
                uiState = settingsUiState,
                onThemeSelected = settingsViewModel::setThemeMode,
                onLanguageSelected = settingsViewModel::setLanguage,
                onThemeClick = onThemeClick,
                modifier = Modifier.weight(1f).fillMaxSize(),
            )
        }
    }
}

// 侧边栏导航
@Composable
private fun AppRail(
    selectedTab: AppTab,
    onTabSelected: (AppTab) -> Unit,
) {
    NavigationRail(
        windowInsets = WindowInsets(0, 0, 0, 0),
        header = { Spacer(Modifier.height(32.dp)) },
    ) {
        NavigationRailItem(
            selected = selectedTab == AppTab.HOME,
            onClick = { onTabSelected(AppTab.HOME) },
            icon = { Icon(Icons.Default.Home, stringResource(R.string.home_title)) },
            label = { Text(stringResource(R.string.home_title)) },
        )
        NavigationRailItem(
            selected = selectedTab == AppTab.SETTINGS,
            onClick = { onTabSelected(AppTab.SETTINGS) },
            icon = { Icon(Icons.Default.Settings, stringResource(R.string.settings_title)) },
            label = { Text(stringResource(R.string.settings_title)) },
        )
    }
}