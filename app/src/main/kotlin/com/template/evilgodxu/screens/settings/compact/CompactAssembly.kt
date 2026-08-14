package com.template.evilgodxu.screens.settings.compact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.R
import com.template.evilgodxu.screens.settings.SettingsUiState
import com.template.evilgodxu.screens.settings.compact.app_info.AppInfo
import com.template.evilgodxu.screens.settings.compact.appearance.Appearance
import com.template.evilgodxu.screens.settings.compact.language.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactAssembly(
    uiState: SettingsUiState,
    onBack: () -> Unit,
    onThemeSelected: (com.template.evilgodxu.data.settings.ThemeMode) -> Unit,
    onLanguageSelected: (com.template.evilgodxu.data.settings.AppLanguage) -> Unit,
    onThemeClick: (androidx.compose.ui.geometry.Offset) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, stringResource(R.string.back))
                    }
                },
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Appearance(uiState.themeMode, onThemeSelected, onThemeClick)
            Language(uiState.language, onLanguageSelected)
            AppInfo(uiState.version)
        }
    }
}
