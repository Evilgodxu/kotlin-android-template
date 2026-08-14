package com.template.evilgodxu.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.window.core.layout.WindowSizeClass
import com.template.evilgodxu.screens.settings.compact.CompactAssembly
import com.template.evilgodxu.screens.settings.expanded.ExpandedAssembly
import com.template.evilgodxu.theme.LocalThemeTransitionController
import com.template.evilgodxu.theme.rememberWindowSizeClass
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val transitionController = LocalThemeTransitionController.current
    val windowSizeClass = rememberWindowSizeClass()
    val onThemeClick: (Offset) -> Unit = transitionController::revealAt
    if (windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        ExpandedAssembly(uiState, onBack, viewModel::setThemeMode, viewModel::setLanguage, onThemeClick)
    } else {
        CompactAssembly(uiState, onBack, viewModel::setThemeMode, viewModel::setLanguage, onThemeClick)
    }
}
