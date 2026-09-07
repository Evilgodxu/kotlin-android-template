package com.template.evilgodxu.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.template.evilgodxu.screens.settings.compact.SettingsCompactAssembly
import com.template.evilgodxu.screens.settings.expanded.SettingsExpandedAssembly
import com.template.evilgodxu.theme.LocalThemeTransitionController
import com.template.evilgodxu.ui.windowSize.WindowSizeClass
import com.template.evilgodxu.ui.windowSize.rememberWindowSizeClass
import org.koin.androidx.compose.koinViewModel

// 页面入口：装配状态、按窗口尺寸类分发形态与跨形态副作用，不含布局（规范 13）
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onThemeClick: (Offset) -> Unit = LocalThemeTransitionController.current::revealAt
    when (rememberWindowSizeClass()) {
        WindowSizeClass.Compact -> SettingsCompactAssembly(
            uiState = uiState,
            onBack = onBack,
            onThemeSelected = viewModel::setThemeMode,
            onLanguageSelected = viewModel::setLanguage,
            onThemeClick = onThemeClick,
            modifier = modifier,
        )
        WindowSizeClass.Medium, WindowSizeClass.Expanded -> SettingsExpandedAssembly(
            uiState = uiState,
            onBack = onBack,
            onThemeSelected = viewModel::setThemeMode,
            onLanguageSelected = viewModel::setLanguage,
            onThemeClick = onThemeClick,
            modifier = modifier,
        )
    }
}