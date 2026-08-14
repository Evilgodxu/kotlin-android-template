package com.template.evilgodxu.screens.settings.expanded

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.R
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.ThemeMode
import com.template.evilgodxu.screens.settings.SettingsUiState
import com.template.evilgodxu.screens.settings.dialog.LanguageSelectionDialog
import com.template.evilgodxu.screens.settings.dialog.ThemeSelectionDialog
import com.template.evilgodxu.screens.settings.expanded.app_info.AppInfo
import com.template.evilgodxu.screens.settings.expanded.appearance.Appearance
import com.template.evilgodxu.screens.settings.expanded.language.Language

@Composable
fun ExpandedAssembly(
    uiState: SettingsUiState,
    onBack: () -> Unit,
    onThemeSelected: (ThemeMode) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onThemeClick: (Offset) -> Unit,
) {
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var pendingThemeClickPosition by remember { mutableStateOf(Offset.Zero) }

    Row(modifier = Modifier.fillMaxSize()) {
        IconButton(onClick = onBack, modifier = Modifier.padding(16.dp)) {
            Icon(Icons.Default.ArrowBack, stringResource(R.string.back))
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 32.dp, end = 48.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.headlineMedium,
            )
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
                onLanguageSelected(language)
                showLanguageDialog = false
            },
        )
    }
}