package com.template.evilgodxu.screens.settings.component.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.TemplateAppUiState
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.ThemeMode
import com.template.evilgodxu.screens.settings.component.appInfo.AppInfo
import com.template.evilgodxu.screens.settings.component.appearance.Appearance
import com.template.evilgodxu.screens.settings.component.appearance.ThemeSelectionDialog
import com.template.evilgodxu.screens.settings.component.language.Language
import com.template.evilgodxu.screens.settings.component.language.LanguageSelectionDialog

// 设置页内容单元：设置列表 + 弹窗状态，供各尺寸组装器复用
@Composable
fun SettingsContent(
    uiState: TemplateAppUiState,
    onThemeSelected: (ThemeMode) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onThemeClick: (Offset) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var pendingThemeClickPosition by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        Appearance(
            themeMode = uiState.themeMode,
            onThemeClick = { position ->
                pendingThemeClickPosition = position
                showThemeDialog = true
            },
        )
        Language(uiState.language, onLanguageSelected, onShowDialog = { showLanguageDialog = true })
        AppInfo(uiState.version)
    }

    if (showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = uiState.themeMode,
            onDismiss = { showThemeDialog = false },
            onThemeSelected = { mode ->
                onThemeClick(pendingThemeClickPosition)
                onThemeSelected(mode)
                showThemeDialog = false
            },
        )
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = uiState.language,
            onDismiss = { showLanguageDialog = false },
            onLanguageSelected = { language ->
                showLanguageDialog = false
                onLanguageSelected(language)
            },
        )
    }
}