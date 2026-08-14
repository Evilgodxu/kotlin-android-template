package com.template.evilgodxu.screens.settings.expanded.language

import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.ui.res.stringResource
import com.template.evilgodxu.R
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.screens.settings.expanded.language.component.SettingsClickableItem
import com.template.evilgodxu.screens.settings.expanded.language.component.SettingsSection

@Composable
fun Language(language: AppLanguage, onLanguageSelected: (AppLanguage) -> Unit) {
    SettingsSection(title = stringResource(R.string.settings_section_language)) {
        AppLanguage.entries.forEach { option ->
            SettingsClickableItem(
                icon = Icons.Default.Language,
                title = languageName(option),
                subtitle = if (language == option) stringResource(R.string.settings_selected) else "",
                onClick = { onLanguageSelected(option) },
            )
        }
    }
}

@Composable
private fun languageName(language: AppLanguage): String = when (language) {
    AppLanguage.SYSTEM -> stringResource(R.string.language_system)
    AppLanguage.CHINESE -> stringResource(R.string.language_chinese)
    AppLanguage.ENGLISH -> stringResource(R.string.language_english)
}
