package com.template.evilgodxu.screens.home.expanded.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.template.evilgodxu.R
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.ThemeMode
import com.template.evilgodxu.screens.settings.SettingsViewModel
import com.template.evilgodxu.screens.settings.components.LanguageSelectionDialog
import com.template.evilgodxu.screens.settings.components.SettingsClickableItem
import com.template.evilgodxu.screens.settings.components.SettingsSection
import com.template.evilgodxu.screens.settings.components.ThemeSelectionDialog
import com.template.evilgodxu.screens.settings.rememberCurrentAppLanguage
import org.koin.androidx.compose.koinViewModel

// 设置区：主题与语言选择
@Composable
fun SettingsArea(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentLanguage = rememberCurrentAppLanguage()

    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        SettingsSection(title = stringResource(R.string.settings_section_appearance)) {
            SettingsClickableItem(
                icon = Icons.Default.Palette,
                title = stringResource(R.string.settings_theme_title),
                subtitle = when (uiState.themeMode) {
                    ThemeMode.SYSTEM -> stringResource(R.string.theme_system)
                    ThemeMode.DARK -> stringResource(R.string.theme_dark)
                    ThemeMode.LIGHT -> stringResource(R.string.theme_light)
                },
                onClick = { showThemeDialog = true },
            )
        }

        SettingsSection(title = stringResource(R.string.settings_section_language)) {
            SettingsClickableItem(
                icon = Icons.Default.Language,
                title = stringResource(R.string.settings_language_title),
                subtitle = when (currentLanguage) {
                    AppLanguage.SYSTEM -> stringResource(R.string.language_system)
                    AppLanguage.CHINESE -> stringResource(R.string.language_chinese)
                    AppLanguage.ENGLISH -> stringResource(R.string.language_english)
                },
                onClick = { showLanguageDialog = true },
            )
        }
    }

    if (showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = uiState.themeMode,
            onDismiss = { showThemeDialog = false },
            onThemeSelected = { themeMode ->
                viewModel.setThemeMode(themeMode)
                showThemeDialog = false
            },
        )
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = currentLanguage,
            onDismiss = { showLanguageDialog = false },
            onLanguageSelected = { language ->
                showLanguageDialog = false
                viewModel.setLanguage(language)
            },
        )
    }
}
