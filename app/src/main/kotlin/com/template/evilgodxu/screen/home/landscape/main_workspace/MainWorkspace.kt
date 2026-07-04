package com.template.evilgodxu.screen.home.landscape.main_workspace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.R
import com.template.evilgodxu.screen.home.landscape.left_panel.LandscapeTab
import com.template.evilgodxu.screen.home.landscape.main_workspace.home_summary.HomeSummary
import com.template.evilgodxu.screen.home.landscape.main_workspace.settings.SettingsPanel

@Composable
fun MainWorkspace(
    selectedTab: LandscapeTab,
    themeMode: String,
    language: String,
    onThemeModeChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        when (selectedTab) {
            LandscapeTab.HOME -> {
                HomeSummary()
            }

            LandscapeTab.SETTINGS -> {
                Text(
                    text = stringResource(R.string.settings_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                SettingsPanel(
                    themeMode = themeMode,
                    language = language,
                    onThemeModeChange = onThemeModeChange,
                    onLanguageChange = onLanguageChange,
                )
            }
        }
    }
}
