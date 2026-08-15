package com.template.evilgodxu.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.template.evilgodxu.screens.settings.compact.CompactAssembly
import com.template.evilgodxu.theme.LocalThemeTransitionController
import org.koin.androidx.compose.koinViewModel

// 紧凑布局页面入口
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onThemeClick: (Offset) -> Unit = LocalThemeTransitionController.current::revealAt
    CompactAssembly(
        uiState = uiState,
        onBack = onBack,
        onThemeSelected = viewModel::setThemeMode,
        onLanguageSelected = viewModel::setLanguage,
        onThemeClick = onThemeClick,
        modifier = modifier,
    )
}