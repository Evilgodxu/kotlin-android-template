package com.template.evilgodxu.screen.home.landscape.top_toolbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.template.evilgodxu.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopToolbar(
    themeMode: String,
    language: String,
    onThemeModeChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(stringResource(R.string.home_title)) },
        actions = {
            SettingsMenu(
                themeMode = themeMode,
                language = language,
                onThemeModeChange = onThemeModeChange,
                onLanguageChange = onLanguageChange,
            )
        },
    )
}
